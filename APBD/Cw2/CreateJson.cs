using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;
using System.Collections;

namespace cw2
{
    class CreateJson
    {
        public University university { get; set; }
        public void CreateFile(List<Student> person, Hashtable std, string jsonPath)
        {
            var options = new JsonSerializerOptions
            {
                WriteIndented = true
            };

            CreateJson tmp = new CreateJson
            {
                university = new University()
                {
                    createdAt = DateTime.Today.Date.ToString("d"),
                    author = "Konstantsin Puchko",
                    studenci = person,
                    activeStudies = std

                }
            };

            var jsonString = JsonSerializer.Serialize(tmp, options);

            File.WriteAllText(jsonPath + @"\result.json", jsonString);

            Console.WriteLine("JSON file created!");
        }
    }
}
