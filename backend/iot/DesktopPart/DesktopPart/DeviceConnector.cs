using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Text;
using System.Runtime.InteropServices;
using System.IO;
using System.Reflection;
using System.Runtime.CompilerServices;
using System.Diagnostics;
using Microsoft.CSharp;
using System.CodeDom.Compiler;
using Newtonsoft.Json;

namespace DesktopPart
{
    public class DeviceConnector
    {
        public static List<string> connections = Service.getConnectionStrings();
        static List<IntPtr> handles = Service.getHandles(connections.Count);
        static IntPtr h = IntPtr.Zero;
        [DllImport("C:\\WINDOWS\\system32\\plcommpro.dll", EntryPoint = "Connect")]
        private static extern IntPtr Connect(string Parameters);
        [DllImport("plcommpro.dll", EntryPoint = "PullLastError")]
        private static extern int PullLastError();

        public static IntPtr connect(IntPtr handle, string connectionString)
        {
            int ret = 0;
            if (IntPtr.Zero == h)
            {
                h = Connect(connectionString);
                if (h != IntPtr.Zero)
                {
                    Console.WriteLine("Connect device succeed!");
                }
                else
                {
                    ret = PullLastError();
                    Console.WriteLine("Connect device Failed! The error id is: " + ret);
                }

            }
            return h;
        }

        [DllImport("plcommpro.dll", EntryPoint = "Disconnect")]
        private static extern void Disconnect(IntPtr h);
        public static void disconnect()
        {
            if (IntPtr.Zero != h)
            {
                Disconnect(h);
                h = IntPtr.Zero;
            }
        }

        [DllImport("plcommpro.dll", EntryPoint = "GetDeviceData")]
        private static extern int GetDeviceData(IntPtr h, ref byte buffer, int buffersize, string tablename, string filename, string filter, string options);

        static string strcount = "";

        private static void getTransactionData(string connectionString)
        {
            IntPtr h1 = h;
            int ret = 0;
            int BUFFERSIZE = 10 * 1024 * 1024;
            byte[] buffer = new byte[BUFFERSIZE];
            string devtablename = "transaction";
            string str = "*";
            string devdatfilter = "";
            string options = "";
            try
            {
                ret = GetDeviceData(h, ref buffer[0], BUFFERSIZE, devtablename, str, devdatfilter, options);
                Console.WriteLine("getTransactionData#beforeRET");
            }
            catch (Exception)
            {
                disconnect();
                connect(h, connectionString);
                getTransactionData(connectionString);
                return;
            }
            if (ret >= 0)
            {
                strcount = Encoding.Default.GetString(buffer).Trim();
                List<Transaction> list = Service.parse(strcount, connectionString);
                if (list.Count >= 100)
                {
                    truncateTrunsactionTable(h);
                }
                Console.WriteLine("List: {0} Transactions {1}, list==transactions {2}", list.Count, Service.transactions.Count, list.Count == Service.transactions.Count);
                if (list.Count == Service.transactions.Count)
                {
                    return;
                }
                else
                {
                    DateTime time = Service.getLastRegisteredTime();
                    List<Transaction> actualTransactions = Service.prepareActualTransactions(list, time);
                    int step = 30;
                    if (actualTransactions.Count < step)
                    {
                        WS.SendData(JsonConvert.SerializeObject(actualTransactions));
                        return;
                    }
                    for (int i = 0; i < actualTransactions.Count; i += step)
                    {
                        if (i + step > actualTransactions.Count)
                        {
                            WS.SendData(JsonConvert.SerializeObject(actualTransactions.GetRange(i, actualTransactions.Count - i)));
                        }
                        else
                        {
                            WS.SendData(JsonConvert.SerializeObject(actualTransactions.GetRange(i, step)));
                        }
                    }
                }
                return;
            }
            else
            {
                disconnect();
                connect(h, connectionString);
                getTransactionData(connectionString);
                return;
            }
        }
        [DllImport("plcommpro.dll", EntryPoint = "DeleteDeviceData")]
        public static extern int DeleteDeviceData(IntPtr h, string tablename, string data, string options);
        private static void truncateTrunsactionTable(IntPtr h)
        {
            int ret = 0;
            string tablename = "transaction";
            string data = "Time_second>0";
            string options = "";
            if (IntPtr.Zero != h)
            {
                ret = DeleteDeviceData(h, tablename, data, options);
            }
        }
        public static void deleteUser(string cardNumber)
        {
            int ret = 0;
            string tablename = "user";
            string data = "CardNo=" + cardNumber;
            string options = "";
            foreach (IntPtr h in handles)
            {
                if (IntPtr.Zero != h)
                {
                    ret = DeleteDeviceData(h, tablename, data, options);
                }
            }
        }
        [DllImport("plcommpro.dll", EntryPoint = "SetDeviceData")]
        public static extern int SetDeviceData(IntPtr h, string tablename, string data, string options);
        public static void insertUser(string cardNumber)
        {
            int ret = 0;
            string tablename = "user";
            string data = "Pin=" + cardNumber + "\tCardNo=" + cardNumber + "\tPassword=" + "\r\n";
            string options = "";
            foreach (IntPtr h in handles)
            {
                ret = SetDeviceData(h, tablename, data, options);
                tablename = "userauthorize";
                data = "Pin=" + cardNumber + "\tAuthorizeTimezoneId=1\tAuthorizeDoorId=3\r\n";
                ret = SetDeviceData(h, tablename, data, options);
            }
        }
        public static void doRead()
        {
            for (int i = 0; i < handles.Count; i++)
            {
               handles[i]=connect(handles[i], connections[i]);
               getTransactionData(connections[i]);
            }
            WS.lastDate = DateTime.Now;
        }

    }
}

