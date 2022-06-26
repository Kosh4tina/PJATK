using System.Collections;
using System.Collections.Generic;

namespace cw2
{
    class University
    {
        public string createdAt { get; set; }
        public string author { get; set; }
        public List<Student> studenci { get; set; }
        public Hashtable activeStudies { get; set; }
    }
}
