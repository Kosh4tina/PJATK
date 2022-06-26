using System;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Cryptography;
using System.Text.RegularExpressions;
using Cw7.DTOs.Requests;
using Cw7.Services.AuthenticationServices;
using Microsoft.AspNetCore.Mvc;
using static System.Security.Claims.ClaimTypes;

namespace Cw7.Controllers
{
    [ApiController]
    [Route("api/login")]
    public class LoginController : ControllerBase
    {
        private const int RefreshTokenSize = 32;
        private readonly IAuthenticationService _authenticationService;

        public LoginController(IAuthenticationService authenticationService)
        {
            _authenticationService = authenticationService;
        }

        [HttpPost]
        public IActionResult Login(LoginRequest loginRequest)
        {
            var jwtSecurityToken = _authenticationService.Login(loginRequest.Username, loginRequest.Password);
            if (jwtSecurityToken == null) return Unauthorized("Przekazano zla nazwe uzytkownika lub haslo!");
            var refreshToken = GenerateRefreshToken();
            _authenticationService.UpdateRefreshToken(loginRequest.Username, refreshToken);
            return Ok(new
            {
                accessToken = new JwtSecurityTokenHandler().WriteToken(jwtSecurityToken), refreshToken
            });
        }

        [HttpPost("refresh-token")]
        public IActionResult RefreshToken(RefreshTokenRequest refreshTokenRequest)
        {
            var jwt = ExtractJwtFromRequestHeaders();
            var claimsPrincipal = _authenticationService.ValidateJwtAndGetClaimsPrincipal(jwt);
            if (claimsPrincipal == null) return Unauthorized("Invalid JWT!");
            var username = claimsPrincipal.Claims.First(claim => claim.Type == Name).Value;
            var refreshToken = refreshTokenRequest.RefreshToken;
            var jwtSecurityToken = _authenticationService.RefreshJwt(username, refreshToken);
            if (jwtSecurityToken == null) return Unauthorized("Invalid refresh token!");
            var newRefreshToken = GenerateRefreshToken();
            _authenticationService.UpdateRefreshToken(username, newRefreshToken);
            return Ok(new
            {
                accessToken = new JwtSecurityTokenHandler().WriteToken(jwtSecurityToken),
                refreshToken = newRefreshToken
            });
        }

        private string ExtractJwtFromRequestHeaders()
        {
            const string jwtRegex = @"Bearer .+";
            return HttpContext.Request.Headers["Authorization"]
                .First(stringValue => Regex.Matches(stringValue, jwtRegex).Count != 0)
                .Replace("Bearer ", "");
        }

        private static string GenerateRefreshToken()
        {
            using var rngProvider = new RNGCryptoServiceProvider();
            var randomNumber = new byte[RefreshTokenSize];
            rngProvider.GetBytes(randomNumber);
            return Convert.ToBase64String(randomNumber);
        }
    }
}