using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;
using WebSocketSharp;
using System.Threading;
using System.IO;
using System.Reflection;

namespace DesktopPart
{

    class Program
    {
        private static Timer timer;
        
        static void Main(string[] args)
        {
            timer = new System.Threading.Timer(
                 e => DeviceConnector.doRead(),
                 null,
                 TimeSpan.Zero,
                 TimeSpan.FromSeconds(5));
            Console.ReadKey();
        }
    }
}
