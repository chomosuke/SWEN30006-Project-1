f = open("output.txt")

output = f.read()
output = output.split('\n')

import re
for line in output:
  if re.match('T: ... > Delivered\( ...\) .[]', line) != None:
    

f.close()
