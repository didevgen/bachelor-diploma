using System;
using ZKAccess.Services;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ZKAccess.Entity;

namespace ZKAccess.State
{
    public sealed class ConnectionContainer : IConnection
    {
        private ConnectionContainer()
        {
        }

        public static ConnectionContainer Instance { get { return lazy.Value; } }

        private static readonly Lazy<ConnectionContainer> lazy =
        new Lazy<ConnectionContainer>(() => new ConnectionContainer());

        private IDictionary<string, IntPtr> connections = new Dictionary<string, IntPtr>();

        public void Connect(string deviceId, ConnectionParams parameters)
        {
            if (!this.connections.ContainsKey(deviceId))
                this.ConnectWithBreakingConnection(deviceId, parameters, 10 * 60 * 1000);
        }

        public bool Disconnect(string deviceId)
        {
            if (this.connections.ContainsKey(deviceId))
            {
                IntPtr handle = this.connections[deviceId];
                this.connections.Remove(deviceId);
                try
                {
                    ZKService.Disconnect(handle);
                    return true;
                } catch (Exception ex)
                {
                    return false;
                }
            } else
            {
                return true;
            }
        }

        private async void ConnectWithBreakingConnection(string deviceId, ConnectionParams parameters,  int timeout)
        {
            var task = Task.Run(() =>
            {
                IntPtr handle = ZKService.Connect(parameters.ToString());
                connections.Add(deviceId, handle);
            });
            if (await Task.WhenAny(task, Task.Delay(timeout)) == task)
            {
                this.Disconnect(deviceId);
            }
        }
    }
}
