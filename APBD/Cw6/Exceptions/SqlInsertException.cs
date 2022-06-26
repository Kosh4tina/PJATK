using System;

namespace Cw6.Exceptions
{
    public class SqlInsertException : Exception
    {
        public SqlInsertException() { }
        public SqlInsertException(string message) : base(message) { }
    }
}