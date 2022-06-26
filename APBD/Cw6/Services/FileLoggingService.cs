using System;
using System.IO;

namespace Cw6.Services
{
    public class FileLoggingService : ILoggingService
    {
        private const string LogFilename = "requestsLog.txt";

        public void Log(object message)
        {
            File.AppendAllText(LogFilename, message + Environment.NewLine);
        }
    }
}