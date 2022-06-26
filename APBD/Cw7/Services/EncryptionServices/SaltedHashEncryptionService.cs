using System;
using System.Linq;
using System.Security.Cryptography;

namespace Cw7.Services.EncryptionServices
{
    public class SaltedHashEncryptionService : IEncryptionService
    {
        private const int SaltSize = 32;
        private const int HashSize = 512;
        private const int Iterations = 100000;

        public string Encrypt(string value)
        {
            return Convert.ToBase64String(EncryptWithSalt(value, PrepareSalt()));
        }

        public bool Verify(string candidatePlainString, string existingHashString)
        {
            if (string.IsNullOrEmpty(candidatePlainString) || string.IsNullOrEmpty(existingHashString)) return false;
            var existingHashBytes = Convert.FromBase64String(existingHashString);
            var salt = ExtractSaltFromHash(existingHashBytes);
            return EncryptWithSalt(candidatePlainString, salt).SequenceEqual(existingHashBytes);
        }

        private static byte[] PrepareSalt()
        {
            using var rngProvider = new RNGCryptoServiceProvider();
            var salt = new byte[SaltSize];
            rngProvider.GetBytes(salt);
            return salt;
        }

        private static byte[] EncryptWithSalt(string value, byte[] salt)
        {
            var rfc2898DeriveBytes = new Rfc2898DeriveBytes(value, salt, Iterations, HashAlgorithmName.SHA512);
            var hashedValue = rfc2898DeriveBytes.GetBytes(HashSize);
            return salt.Concat(hashedValue).ToArray();
        }

        private static byte[] ExtractSaltFromHash(byte[] hash)
        {
            var salt = new byte[SaltSize];
            Array.Copy(hash, salt, SaltSize);
            return salt;
        }
    }
}