using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ZKService.Entities;
using ZKService.Services;

namespace ZKService.State
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

        public bool Connect(string deviceId, ConnectionParams parameters)
        {
            if (!this.connections.ContainsKey(deviceId))
            {
                try
                {
                    this.BreakConnection(deviceId, 10 * 60 * 1000);
                    IntPtr handle = ZKApi.Connect(parameters.ToString());
                    connections.Add(deviceId, handle);
                    return true;
                }
                catch (Exception ex)
                {
                    return false;
                }
            }
            return true;
        }

        public bool Disconnect(string deviceId)
        {
            if (this.connections.ContainsKey(deviceId))
            {
                IntPtr handle = this.connections[deviceId];
                this.connections.Remove(deviceId);
                try
                {
                    ZKApi.Disconnect(handle);
                    return true;
                }
                catch (Exception ex)
                {
                    return false;
                }
            }
            else
            {
                return true;
            }
        }

        private async void BreakConnection(string deviceId, int timeout)
        {
            await Task.Delay(timeout).ContinueWith(t => {
                this.Disconnect(deviceId);
            });
        }
    }
}
