using System;

namespace Cw7.Exceptions
{
    public class SqlInsertException : Exception
    {
        public SqlInsertException() { }
        public SqlInsertException(string message) : base(message) { }
    }
}