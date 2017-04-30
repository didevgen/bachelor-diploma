using System;
using System.Runtime.InteropServices;

namespace ZKService.Services
{
    public class ZKApi
    {
        /*
         * The function is used to connect a device.After 
         * the connection is successful, the connection handle is returned.
         * Parameters:
         * [in]: Specify the connection options through the parameter, for example:
         * "protocol=RS485,port=COM2,baudrate=38400bps,deviceid=1,timeout=50000, passwd=”;
         * “protocol=TCP,ipaddress=192.168.12.154,port=4370,timeout=4000,passwd=”;
         * To connect a device, the system needs to transfer the device-related 
         * connection parameters.
            protocol indicates the protocol used for communication. 
            At present, RS485 and TCP can be used.

            port: Communication port of the device. For example, 
            if the RS485 protocol is used, you can set port to COM1: 
            If the TCP is used, the default port is 4370 unless otherwise noted.

            deviceid: Device ID used by the serial port.

            baudrate: Baud rate used for the communication of the communication 
            of the serial port.

            ipaddress: IP address of the related device for TCP/IP communication.

            timeout: Timeout time of the connection (unit: ms)If the network 
            connection is in poor condition, you should set the parameter 
            to a larger value. Usually, timeout=5000 (5 seconds) can meet the 
            basic network needs. When the query result contains the error code of -2,
            you should set timeout to a larger value, for example, timeout=20000 
            (20 seconds).

            passwd: Connection password of the communication. 
            If the parameter value is null, it indicates that no password is used.
            (Note: The connection parameters are case-sensitive)
        */

        [DllImport("plcommpro.dll", EntryPoint = "Connect")]
        public static extern IntPtr Connect(string Parameters);

        /*
         * [Objective]
            The function is used to disconnect the device.
            [Parameter description]
            handle
            [in]: The handle that is returned when the connection is successful.
            [Returned value]
            None
         */
        [DllImport("plcommpro.dll", EntryPoint = "Disconnect")]
        public static extern void Disconnect(IntPtr h);


        [DllImport("plcommpro.dll", EntryPoint = "PullLastError")]
        public static extern int PullLastError();


        /*
         * [Objective]
            The function is used to read the controller parameters, for example, 
            the device ID, door sensor type, driving time of the lock, and read
            interval.
            [Parameter description]
            handle
            [in]: The handle that is returned when the connection is successful.
            Buffer
            [in]: The buffer used to receive the returned data; the returned 
            data is expressed in a text format; if the returned data is multiple
            params, the multiple params are separated by commas.
            BufferSize
            [in]	The size of the buffer used to receive the returned data.
            Items
            [in]: The parameter names of the device to be read; the multiple 
            parameter names are separated by commas; you can read at most 
            20 parameters at a time (Attached table 1 lists the parameter value 
            attributes).
            [Returned value]
            When the returned value is 0 or a positive value, it indicates that the operation is successful. When the returned value is a negative value, it indicates that the operation fails. Attached table 5 lists the information about the error codes.
         */
        [DllImport("plcommpro.dll", EntryPoint = "GetDeviceParam")]
        public static extern int GetDeviceParam(IntPtr h, ref byte buffer, int buffersize, string itemvalues);

        /*
         * [Objective]
            The function is used to set the controller parameters, for example,
            the device ID, door sensor type, driving time of the lock, 
            and read interval.
            [Parameter description]
            handle
            [in]: The handle that is returned when the connection is successful.
            ItemValues
            [in]: The device parameter value to be set; the multiple parameter 
            values can be separated by commas; you can set at most 20 parameters
            at a time (Attached table 2 lists the parameter value attributes).
            [Returned value]
            When the returned value is 0 or a positive value, 
            it indicates that the operation is successful. 
            When the returned value is a negative value, it indicates an error.
            Attached table 5 lists the information about the error codes.
         */
        [DllImport("plcommpro.dll", EntryPoint = "SetDeviceParam")]
        public static extern int SetDeviceParam(IntPtr h, string itemvalues);


        /*
         * [Objective]
        The function is used to control the actions of the controller.
        [Parameter description]
        handle
        [in]: The handle that is returned when the connection is successful.
        OperationID
        [in] Operation contents: 1 for output, 2 for cancel alarm, 
        3 for restart device, and 4 for enable/disable normal open state.

        Param1
        [in]	 When the OperationID is output operation: 
        If Param2 is the door output the parameter indicates the door number.
        If Param2 is auxiliary output, the parameter indicates the number
        of the auxiliary output interface (for details, see Attached table 3).
        If Param2 is cancel alarm, the parameter value is 0 by default. 
        
        Param2
        [in]: When the OperationID is output operation, this parameter indicates
        the address type of the output point (1 for door output, 2 for auxiliary output), 
        for details, see Attached table 3. When the OperationID is cancel alarm,, 
        the parameter value is 0 by default. When the OperationID value is 4,
        that is enable/disable normal open state, the parameter indicates 
        is enable/disable normal open state (0 for disable, 1 for enable).
        
        Param3
        [in]: When the OperationID is output operation, the parameter indicates 
        the door-opening time (0 indicates the closed state, 255 indicates the normal
        open state, the value range is 1 to 60 seconds). The default value is 0.
        
        Param4
        [in] Reserved; the default value is 0.
        
        Option
        [in]: The default value is null; it is used for extension.
        [Returned value]
        When the returned value is 0 or a positive value, it indicates that the operation is successful.
        When the returned value is a negative value, it indicates that the operation fails. 
        Attached table 5 lists the information about the error codes.

         */
        [DllImport("plcommpro.dll", EntryPoint = "ControlDevice")]
        public static extern int ControlDevice(IntPtr h, int operationid, int param1, int param2, int param3, int param4, string options);

        /*
         * [Objective]
        The function is used to read the total number of records on the device 
        and return the number of records for the specified data.
        [Parameter description]
        Handle
        [in]: The handle that is returned when the connection is successful.
        TableName
        [in]: Data table name. Attached table 4 lists the available data tables.
        Filter
        [in]: The default value is null; it is used for extension.
        Options
        [in]: The default value is null; it is used for extension.
        [Returned value]
        When the returned value is 0 or a positive value, it indicates that the 
        operation is successful (the returned value indicates the number of records).
        When the returned value is a negative value, it indicates that the operation 
        fails. Attached table 5 lists the information about the error codes.

         */
        [DllImport("plcommpro.dll", EntryPoint = "GetDeviceDataCount")]
        public static extern int GetDeviceDataCount(IntPtr h, string tablename, string filter, string options);

        /*
         * [Objective]
        The function is used to read the device data (for example, the punch records, time segment, 
        user information, and holiday information). The data can be one or multiple records.
        [Parameter description]
        handle
        [in]: The handle that is returned when the connection is successful.
        Buffer
        [in]: The buffer used to receive the returned data; the returned data is expressed 
        in a text format; if the returned data is multiple records, the multiple records are separated by \r\n.
        BufferSize
        [in]	The size of the buffer used to receive the returned data.
        TableName
        [in]: Data table name. Attached table 4 lists the available data tables.
        FieldNames
        [in]: Field name list; the multiple fields are separated by semicolons; 
        * indicates all fields, and the first line in the returned data field is the field names.
        Filter
        [in]: The conditions of reading the data; the character string in the format 
        of “field name, operator, value” can support multiple conditions, which are separated by commas; for example:
        <Field name>=<Value>(no space is allowed at two sides of =)
        Options
        [in]: Only used to download the access control records; when the parameter value 
        is NewRecord, new records are downloaded. When the value is null, all records are downloaded.
        [Returned value]
        When the returned value is 0 or a positive value, it indicates that the operation 
        is successful (the returned value indicates the number of records). When the returned value 
        is a negative value, it indicates that the operation fails. Attached table 5 lists the information 
        about the error codes.
         */
        [DllImport("plcommpro.dll", EntryPoint = "GetDeviceData")]
        public static extern int GetDeviceData(IntPtr h, ref byte buffer, int buffersize, string tablename, string filename, string filter, string options);

        /*
         * [Objective]
        The function is used to set the device data (for example, the time segment, user information, 
        and holiday information). The device data can be one or multiple records.
        [Parameter description]
        handle
        [in]: The handle that is returned when the connection is successful.
        TableName
        [in]: Data table name. Attached table 4 lists the available data tables.
        Data
        [in]: Data record; the data is expressed in a text format; the multiple records 
        are separated by \r\n, and the “Field=Value” pairs are separated by \t.
        Options
        [in]: The default value is null; it is used for extension.
        [Returned value]
        When the returned value is 0 or a positive value, it indicates that the operation is successful.
        When the returned value is a negative value, it indicates that the operation fails.
        Attached table 5 lists the information about the error codes.
         */
        [DllImport("plcommpro.dll", EntryPoint = "SetDeviceData")]
        public static extern int SetDeviceData(IntPtr h, string tablename, string data, string options);

        [DllImport("plcommpro.dll", EntryPoint = "GetRTLog")]
        public static extern int GetRTLog(IntPtr h, ref byte buffer, int buffersize);

        [DllImport("plcommpro.dll", EntryPoint = "SearchDevice")]
        public static extern int SearchDevice(string commtype, string address, ref byte buffer);

        [DllImport("plcommpro.dll", EntryPoint = "ModifyIPAddress")]
        public static extern int ModifyIPAddress(string commtype, string address, string buffer);

        [DllImport("plcommpro.dll", EntryPoint = "GetDeviceFileData")]
        public static extern int GetDeviceFileData(IntPtr h, ref byte buffer, ref int buffersize, string filename, string options);

        [DllImport("plcommpro.dll", EntryPoint = "SetDeviceFileData")]
        public static extern int SetDeviceFileData(IntPtr h, string filename, ref byte buffer, int buffersize, string options);

        /*
         * [Objective]
        The function is used to delete the data (for example, user information and time segment) on the device.
        [Parameter description]
        handle
        [in]: The handle that is returned when the connection is successful.
        TableName
        [in]: Data table name. Attached table 4 lists the available data tables.
        Data
        [in]: Data record; the data is expressed in a text format; the multiple records 
        are separated by \r\n, and the “Field=Value” pairs are separated by \t.
        Options
        [in]: The default value is null; it is used for extension.
        [Returned value]
        When the returned value is 0 or a positive value, it indicates that the operation is successful.
        When the returned value is a negative value, it indicates that the operation fails.
        Attached table 5 lists the information about the error codes.
         */
        [DllImport("plcommpro.dll", EntryPoint = "DeleteDeviceData")]
        public static extern int DeleteDeviceData(IntPtr h, string tablename, string data, string options);

        [DllImport("C:\\WINDOWS\\system32\\plcommpro.dll", EntryPoint = "ProcessBackupData")]
        public static extern int ProcessBackupData(byte[] data, int fileLen, ref byte Buffer, int BufferSize);

    }
}
