using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.IO;
using WebSocketSharp;

namespace DesktopPart
{
    class Service
    {
        public static List<Transaction> transactions = new List<Transaction>();

        public static List<Transaction> parse(String text, String connStr)
        {
            List<Transaction> list = new List<Transaction>();
            text = text.Replace("\0", "");
            string[] itemsList = text.Split('\r');
            for (int i = 1; i < itemsList.Length-1; i++)
            {
                try {
                    String[] array = itemsList[i].Split(',');
                    int startIndex = connStr.IndexOf("ipaddress=")+ "ipaddress=".Length;
                    int finishIndex = connStr.IndexOf(",port");
                    string ip = connStr.Substring(startIndex, finishIndex - startIndex);
                    ip=ip+"-" + parseInt(array[3]);
                    ip=ip+"-"+ parseInt(array[5]);
                    Transaction transaction = new Transaction();
                    transaction.cardId = parseInt(array[0].Substring(1));
                    transaction.pin = parseInt(array[1]);
                    transaction.verified = parseInt(array[2]);
                    transaction.doorId = ip;
                    transaction.eventType = parseInt(array[4]);
                    transaction.inOutState = parseInt(array[5]);
                    long time1 = long.Parse(array[6]);
                    //transaction.time = getDate(time1);
                    transaction.time = getDateMiliseconds(time1);
                    transaction.timeString = new DateTime(getDateMiliseconds(time1) * TimeSpan.TicksPerMillisecond).ToString();
                    list.Add(transaction);
                }catch (Exception ex)
                {
                    return null;
                }
            }
            return list;
        }

        private static int parseInt(String text)
        {
            return Int32.Parse(text);
        }

        private static DateTime getDate(long myNewTime)
        {
            DateTime tt = new DateTime(1999, 9, 16);
            long startDate = tt.Ticks / TimeSpan.TicksPerSecond;
            long myNewDate = startDate + myNewTime - 86400;
            return new DateTime(myNewDate * TimeSpan.TicksPerSecond);
        }

        private static long getDateMiliseconds(long myNewTime)
        {
            DateTime tt = new DateTime(1999, 9, 16);
            long startDate = tt.Ticks / TimeSpan.TicksPerSecond;
            long myNewDate = startDate + myNewTime - 86400;
            return new DateTime(myNewDate * TimeSpan.TicksPerSecond).Ticks/TimeSpan.TicksPerMillisecond;
        }
        public static List<Transaction> prepareActualTransactions(List<Transaction> newList, DateTime actualTime)
        {
            List<Transaction> result = new List<Transaction>();
            long actLong = actualTime.Ticks / TimeSpan.TicksPerMillisecond;
            if (newList.Count < transactions.Count)
            {
                transactions.Clear();
                transactions.AddRange(newList);
                return newList;
            }
            foreach (Transaction transaction in newList)
            {
                if (transaction.time>actLong)
                {
                    transactions.Add(transaction);
                    result.Add(transaction);
                }
            }
            return result;
        }
        public static List<Transaction> substract(List<Transaction> newList)
        {
            List<Transaction> result = new List<Transaction>();
            if (transactions.Count == 0)
            {
                transactions.AddRange(newList);
                return newList.GetRange(newList.Count - 10, 10);
            }
            if (newList.Count< transactions.Count)
            {
                transactions.Clear();
                transactions.AddRange(newList);
                return newList;
            }
            for(int i = transactions.Count;i< newList.Count;i++ )
            {
                if (!transactions.Contains(newList.ElementAt(i)))
                {
                    transactions.Add(newList.ElementAt(i));
                    result.Add(newList.ElementAt(i));
                }
            }
            if (result.Count>10)
            {
                return result.GetRange(result.Count - 10, 10);
            }
            return result;
        }
        public static DateTime getLastRegisteredTime()
        {
            DateTime initTime = WS.lastDate;
            WS.SendData("getLastDate");
            while (initTime.Equals(WS.lastDate))
            {
            }
            return WS.lastDate;
        }
        public static void getLastUnResolvedTime()
        {
            WebRequest request = WebRequest.Create("http://localhost:8080/kovaljov/transaction/get/last");
            request.Method = "POST";
            string postData = "This is a test that posts this string to a Web server.";
            byte[] byteArray = Encoding.UTF8.GetBytes(postData);
            request.ContentType = "application/x-www-form-urlencoded";
            request.ContentLength = byteArray.Length;
            Stream dataStream = request.GetRequestStream();
            dataStream.Write(byteArray, 0, byteArray.Length);
            dataStream.Close();
            WebResponse response = request.GetResponse();
            dataStream = response.GetResponseStream();
            StreamReader reader = new StreamReader(dataStream);
            string responseFromServer = reader.ReadToEnd();
            //long time = Int64.Parse(responseFromServer) + 2*3600*1000;
            //DateTime time1 = new DateTime(time * TimeSpan.TicksPerMillisecond);
            Console.WriteLine(responseFromServer);
            DateTime time = DateTime.Parse(responseFromServer);
           
            Console.WriteLine(time);
            Console.WriteLine(DateTime.Now.Ticks / TimeSpan.TicksPerMillisecond);
            reader.Close();
            dataStream.Close();
            response.Close();
        }

        public static List<String> getConnectionStrings()
        {
            string iniFilePath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "config.ini");
            List<string> strings = new List<string>();
            INI ini = new INI(iniFilePath);
            int i = 0;
            for (;;)
            {
                string s = ini.IniReadValue("IP ADDRESSES", "connstr" + i++);
                if (s.Equals(""))
                {
                    break;
                }
                strings.Add(s);
            }
            return strings;
        }
        public static List<WebSocket> getAddresses()
        {
            string iniFilePath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "config.ini");
            List<WebSocket> strings = new List<WebSocket>();
            INI ini = new INI(iniFilePath);
            int i = 0;
            for (;;)
            {
                string s = ini.IniReadValue("WEB SOCKETS", "address" + i++);
                if (s.Equals(""))
                {
                    break;
                }
                strings.Add(new WebSocket(s));
            }
            return strings;
        }
        public static List<IntPtr> getHandles(int size)
        {
            List<IntPtr> lst = new List<IntPtr>();
            for (int i =0;i< size;i++)
            {
                lst.Add(IntPtr.Zero);
            }
            return lst;
        }
    }
}
