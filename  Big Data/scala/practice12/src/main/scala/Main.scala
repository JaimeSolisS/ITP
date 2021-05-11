object Main {

  /*
  [X] Write scripts where you pass arguments to the bash scrips
- Using loops while, for, until and if/else conditions:
- [x] Write shell script for generating fibonacci sequence
- [x] Write shell script for generating prime numbers in a range
- [x] Write shell script for generating pascal triangle
- [x] Write shell script for string palindrome
- [x] Write shell script for finding armstrong numbers
- [X] Find out a way where you can use substring like feature in bash scripting and use that. Example for this is print all the files with same extension O/P should look like:
- .txt - one, two, three
- .pdf - four, five. six
- [] Write a shell script where you read a certain set of commands from a file and execute the, using loops
- [x] For the above script execute a script where you ignore the lines commented(or prefixed by #) and ignore Case sensitivity(use something that ignores the case of letters)
- [x] Find a file which has unseen line feeds or space feeds or tab feeds hidden in a line or in a word. Write a script where you eliminate them and print the original string.

   */

  def main(args: Array[String]): Unit = {

    Fibonacci.fib()
    Prime.prime()
    Pascal.pascal()
    Palindrome.pal()
    Armstrong.arm()
    Substring.files()
    IgnoreComments.printFile()
    Trim.printFile()
  }

}
