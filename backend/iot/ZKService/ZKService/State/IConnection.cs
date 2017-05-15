using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ZKService.Entities;

namespace ZKService.State
{
    interface IConnection
    {
        IntPtr Connect(string deviceId, ConnectionParams connection);

        bool Disconnect(string deviceId);
    }
}
