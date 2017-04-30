using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ZKService.Entities
{
    public class ConnectionParams
    {
        public string protocol { get; set; } = "TCP";

        public string port { get; set; } = "4370";

        public string deviceid { get; set; }

        public string baudrate { get; set; }

        public string ipaddress { get; set; } = "192.168.12.154";

        public string timeout { get; set; } = "10000";

        public string passwd { get; set; } = "";

        public override string ToString()
        {
            return String.Format("protocol={0},ipaddress={1},port={2},timeout={3},passwd={4}",
                         this.protocol, this.ipaddress, this.port, this.timeout, this.passwd);
        }

    }
}
