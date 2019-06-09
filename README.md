## Wiki File Converter
<hr>
<p>This repository is for an application which converts XML reports to Wikitext files. 
<br></p>

### Getting Started
<p>To run the projet and tests you will need to run Gradle or Gradle Wrapper commands:<br>
Add Gradle Wrapper <code>gradle wrapper</code><br>
  Build <code>gradlew build</code><br>
  Testing <code>gradlew test</code><br>
  Or run the tests and application manually.</p>
  <Br>
  <p>When running the application, you'll be asked to specifiy an input and output folders for the .xml input and the desired .wiki output location.<Br>
  If you'd like to stop the application write 'Q'</p>

### Used Technologies
<p><b>Testing: </b>JUnit 5</p>


### Application Description
<p>The application converts .xml files to .wiki files. <br>Right now, converts Sections, Bold, Italic and line breaks to Wikitext format.
<br>
Examples for the .xml report and desired output can be seen in <code>src/main/java/resources/expected/</code> </p>
