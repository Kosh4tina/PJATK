using System;
using System.Net.Http;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Cw1
{
    class Program
    {
        public static async Task Main(string[] args)
        {

            String adr = args.Length > 0 ? args[0] : "https://www.pja.edu.pl";
            var clnt = new HttpClient();
            var jeden = await clnt.GetAsync(adr);

            if (!jeden.IsSuccessStatusCode)
            {
                Console.WriteLine("Błąd połączenia");
                return;
            }

            var WoT = await jeden.Content.ReadAsStringAsync();

            clnt.Dispose();
            jeden.Dispose();

            var reg = new Regex("[a-z]+[a-z0-9]*@[a-z0-9]+\\.[a-z]+", RegexOptions.IgnoreCase);

            var matches = reg.Matches(WoT);

            foreach (var match in matches)
            {
                Console.WriteLine(match.ToString());
            }


        }
    }
}
