# tasksOrganizer(compiled with Oracle JDK-14)

<h2>Prerequisites</h2>

<ul>
	<li><a href="https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html">Oracle jdk14+</a></li>
	<li>mysql or mysql-server (<code>sudo apt install mysql</code>)</li>
</ul>
<br/>
You can see your current java version with the command <code>java --version</code>

<h2>Installation on LINUX</h2>

<ol>
	<li>Download the .jar, .sql and .sh files from a <a href="https://github.com/lamine2000/tasksOrganizer/releases">release</a>.</li>
	<li>Modify the 6th line of the .sh file : <code>jarPath=/the_absolute/path_of/the_jar_file</code></li>
	<li>Execute the .sh file : <code![](execution.gif) >chmod +x <.sh file path> && sh -c <.sh file path></code></li>
	<li>Execute the sql file on mysql or mysql-server : <code>mysql> source <.sql file path></code></li>
</ol>
		
<h2>Execution</h2>
	<code>java -jar <.jar file path></code>
<br/><br/>
![](execution.gif
