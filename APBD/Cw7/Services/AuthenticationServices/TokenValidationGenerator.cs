using System;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using static System.Text.Encoding;

namespace Cw7.Services.AuthenticationServices
{
    public static class TokenValidationGenerator
    {
        private static IConfiguration _configuration;

        public static void AddConfiguration(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        public static TokenValidationParameters GenerateTokenValidationParameters()
        {
            return new TokenValidationParameters
            {
                ValidateIssuer = true,
                ValidateAudience = true,
                ValidateLifetime = true,
                ValidIssuer = _configuration["JwtIssuer"],
                ValidAudience = _configuration["JwtAudience"],
                IssuerSigningKey = new SymmetricSecurityKey(UTF8.GetBytes(_configuration["AuthenticationKey"])),
                ClockSkew = TimeSpan.Zero
            };
        }
    }
}