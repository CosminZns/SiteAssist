<!DOCTYPE html>
<html>
<head>
    <title>Web Crawler and Sitemap Generator README</title>
</head>
<body>

<h1>Web Crawler and Sitemap Generator</h1>

<p>This Java program is designed to crawl a website recursively and generate a JSON sitemap that includes all web pages and their links to other web pages. It uses Jsoup for web scraping.</p>

<h2>Problem Statement</h2>

<p>The goal of this application is to create a site map for a given website, including the following information:</p>

<ul>
    <li>All web pages accessible from the base URL recursively.</li>
    <li>For each web page, the links it has to other web pages, including child links and links of links.</li>
</ul>

<h2>Usage</h2>

<ol>
    <li><strong>Setup:</strong> Ensure you have Java (17) and the required library (Jsoup) installed.</li>
    <li><strong>Configuration:</strong> Update the <code>baseUrl</code> variable in the <code>WebCrawler</code> class to the starting URL of the website you want to crawl.</li>
    <li><strong>Execution:</strong> Run the <code>WebCrawler</code> program to start crawling the website and generating the sitemap.
        <pre>
$ javac WebCrawler.java
$ java WebCrawler
        </pre>
    </li>
    <li><strong>Output:</strong> The program will output the JSON sitemap to the console.</li>
</ol>

<h2>Sample Output</h2>

<p>Here's an example of the JSON sitemap structure:</p>

<pre>
[
  {
    "url": "https://tomblomfield.com/about",
    "childLinks": [
      "https://tomblomfield.com/random",
      "https://tomblomfield.com/archive",
      "https://tomblomfield.com/about",
      "https://tomblomfield.com/rss"
    ]
  }
]
</pre>

<h2>Customization</h2>

<ul>
    <li>You can customize the <code>WebPage</code> class and JSON output structure to suit your specific requirements.</li>
    <li>Adjust the <code>isValidLink</code> method in the <code>WebCrawler</code> class to define valid links according to your needs.</li>
</ul>

<h2>Dependencies</h2>

<ul>
    <li>Jsoup: <a href="https://jsoup.org/">https://jsoup.org/</a></li>
    
</ul>

<h2>License</h2>

<p>This project is licensed under the MIT License - see the <a href="LICENSE">LICENSE</a> file for details.</p>

</body>
</html>