using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using Cw7.DTOs.Responses;
using Cw7.Services.DatabaseServices;
using Cw7.Services.EncryptionServices;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using static System.Security.Claims.ClaimTypes;

namespace Cw7.Services.AuthenticationServices
{
    public class SqlDbAuthenticationService : IAuthenticationService
    {
        private const string SecurityAlgorithm = SecurityAlgorithms.HmacSha512;
        private const int TokenExpiryInMinutes = 5;
        private readonly IConfiguration _configuration;
        private readonly IDbStudentService _dbStudentService;
        private readonly IEncryptionService _encryptionService;

        public SqlDbAuthenticationService(IConfiguration configuration, IDbStudentService dbStudentService,
            IEncryptionService encryptionService)
        {
            _configuration = configuration;
            _dbStudentService = dbStudentService;
            _encryptionService = encryptionService;
        }

        public JwtSecurityToken Login(string username, string password)
        {
            var studentAuthenticationData = _dbStudentService.GetStudentsAuthenticationData(username);
            return !_encryptionService.Verify(password, studentAuthenticationData?.SaltPasswordHash)
                ? null
                : GenerateJwtFromAuthenticationData(studentAuthenticationData);
        }

        public bool UpdateRefreshToken(string username, string refreshToken)
        {
            return _dbStudentService.UpdateRefreshToken(username, refreshToken);
        }

        public ClaimsPrincipal ValidateJwtAndGetClaimsPrincipal(string jwt)
        {
            var tokenValidationParameters = TokenValidationGenerator.GenerateTokenValidationParameters();
            tokenValidationParameters.ValidateLifetime = false;

            var tokenHandler = new JwtSecurityTokenHandler();
            var principal = tokenHandler.ValidateToken(jwt, tokenValidationParameters, out var securityToken);
            if (!(securityToken is JwtSecurityToken jwtSecurityToken) ||
                !jwtSecurityToken.Header.Alg.Equals(SecurityAlgorithm))
                return null;

            return principal;
        }

        public JwtSecurityToken RefreshJwt(string username, string refreshToken)
        {
            var studentAuthenticationData = _dbStudentService.GetStudentsAuthenticationData(username);
            return studentAuthenticationData.RefreshToken != refreshToken
                ? null
                : GenerateJwtFromAuthenticationData(studentAuthenticationData);
        }

        private JwtSecurityToken GenerateJwtFromAuthenticationData(SingleStudentAuthenticationData authenticationData)
        {
            var securityKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["AuthenticationKey"]));
            var signingCredentials = new SigningCredentials(securityKey, SecurityAlgorithms.HmacSha512);
            return new JwtSecurityToken
            (
                _configuration["JwtIssuer"], _configuration["JwtAudience"], PrepareClaims(authenticationData),
                DateTime.UtcNow, DateTime.UtcNow.AddMinutes(TokenExpiryInMinutes), signingCredentials
            );
        }

        private static IEnumerable<Claim> PrepareClaims(SingleStudentAuthenticationData studentAuthenticationData)
        {
            var claims = new List<Claim> {new Claim(Name, studentAuthenticationData.IndexNumber)};
            studentAuthenticationData.Roles.Select(role => new Claim(Role, role)).ToList().ForEach(claims.Add);
            return claims;
        }
    }
}