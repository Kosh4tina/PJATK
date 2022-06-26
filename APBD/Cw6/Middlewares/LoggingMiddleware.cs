using System.IO;
using System.Text;
using System.Threading.Tasks;
using Cw6.Models;
using Cw6.Services;
using Microsoft.AspNetCore.Http;

namespace Cw6.Middlewares
{
    public class LoggingMiddleware
    {
        private readonly ILoggingService _logger;
        private readonly RequestDelegate _next;

        public LoggingMiddleware(RequestDelegate next, ILoggingService logger)
        {
            _next = next;
            _logger = logger;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            if (context.Request != null)
            {
                context.Request.EnableBuffering();
                var method = context.Request.Method;
                var path = context.Request.Path;
                var queryString = context.Request.QueryString.ToString();
                using var reader = new StreamReader(context.Request.Body, Encoding.UTF8, true, 1024, true);
                var body = await reader.ReadToEndAsync();
                context.Request.Body.Position = 0;
                _logger.Log(new LoggingData {Method = method, Path = path, Body = body, QueryString = queryString});
            }

            if (_next != null) await _next(context);
        }
    }
}