using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DesktopPart
{
    class Transaction
    {
        public long cardId { get; set; }

        public int pin { get; set; }

        public int verified { get; set; }

        public string doorId { get; set; }

        public int eventType { get; set; }

        public int inOutState { get; set; }

        public long time { get; set; }

        public String timeString { get; set; }

        public Transaction()
        {

        }

        public override bool Equals(object obj)
        {
            Transaction transaction = (Transaction)obj;
            if (transaction.cardId == this.cardId && transaction.time.Equals(this.time))
            {
                return true;
            }
                return false;
        }




    }
}
