using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WebSocketSharp;

namespace DesktopPart
{
    class WS
    {
        public static List<WebSocket> websockets = Service.getAddresses();
        public static DateTime lastDate = DateTime.Now;
        static WS()
        {
            foreach (WebSocket ws in websockets)
            {
                ws.OnMessage += (sender, e) =>
              setDateTime(e.Data);
                ws.OnError += (sender, e) =>
              Console.WriteLine("Server says: " + e.Exception);
                try {
                    ws.Connect();
                    Console.WriteLine("connected");
                } catch (Exception) {
                    Console.WriteLine("Cannot connect to "+ws.Url);
                }
            }
        }
        private static void setDateTime(string message)
        {
            try
            {
                if (message.Contains("Delete"))
                {
                    Console.WriteLine("Delete");
                    DeviceConnector.deleteUser(message.Substring(message.IndexOf(":") + 1));
                    return;
                }
                else if (message.Contains("Insert"))
                {
                    Console.WriteLine("Insert");
                    DeviceConnector.insertUser(message.Substring(message.IndexOf(":") + 1));
                    return;
                }
                else
                {
                    DateTime time = DateTime.Parse(message);
                    lastDate = time;
                }
            }
            catch(Exception)
            {
                return;
            }
        }
        public static void SendData(String json)
        {
            foreach (WebSocket ws in websockets)
            {
                ws.Send(json);
            }
        }
    }
}
