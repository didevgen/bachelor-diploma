using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ZKAccess.Entity;

namespace ZKAccess.State
{
    interface IConnection
    {
        void Connect(string deviceId, ConnectionParams connection);

        bool Disconnect(string deviceId);
    }
}
