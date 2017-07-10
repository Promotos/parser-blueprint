package de.promotos.parser.blueprint;

import static org.parboiled.errors.ErrorUtils.printParseErrors;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.parboiled.common.FileUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import de.promotos.parser.blueprint.ast.nodes.Node;

@RunWith(Parameterized.class)
public class FileBasedTest extends CalculatorParserTest {

	private static final String BASE_FOLDER = "./src/test/resources/testfiles/";

	@Parameters(name = "{index}: file: {0}")
	public static Collection<Object[]> data() {
		final Collection<Object[]> data = new ArrayList<Object[]>();
		final File[] files = new File(BASE_FOLDER).listFiles(new FilenameFilter() {

			public boolean accept(final File dir, final String name) {
				return name.endsWith(".in");
			}
		});

		for (File f : files) {
			data.add(new Object[] { f });
		}

		return data;
	}

	private File inFile;
	private File expFile;

	public FileBasedTest(final File file) {
		this.inFile = file;
	}

	@Test
	public void parseAndCompare() {
		buildExpFile();
		checkPreconditions(inFile);
		checkPreconditions(expFile);

		final ParsingResult<Node> result = parse();

		Assert.assertFalse("Parse result has errors. " + printParseErrors(result), result.hasErrors());
		
		final String actual = printNodes(result.resultValue);
		final String expected = getExpFileContent();
		Assert.assertEquals(expected,actual);
		
		checkNodeOffsetRecursive(result.resultValue, getInFileContent());
		
		final StringBuilder complCheckResult = checkParsingCompletenessRecursive(result.resultValue, new StringBuilder(getInFileContent()));
		Assert.assertEquals("Not consumed text:\n>" + complCheckResult + "<","", postProcessComplCheckResult(complCheckResult.toString()));
	}
	
	private String postProcessComplCheckResult(final String checkRes) {
		String result = checkRes;
		result = result.replaceAll("\\)", ""); // only the left brace is one node in the ast. No need for a right one. 
		result = result.trim(); // Remove all pending white spaces
		return result;
	}
	
	private StringBuilder checkParsingCompletenessRecursive(final Node n, StringBuilder content) {
		if (n.hasOffset()) {
			content = content.replace(n.getOffsetStart(), n.getOffsetEnd(), expand(' ', n.getOffsetLength()));
		}
		for (Node c : n.getChildren()) {
			content = checkParsingCompletenessRecursive(c, content); 
		}
		return content;
	}
	
	private String expand(char c, int count) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(c);
		}
		return sb.toString();
	}
	
	private void checkNodeOffsetRecursive(final Node n, final CharSequence content) {
		if (n.hasOffset() && n.hasText()) {
			final String snippet = (String) content.subSequence(n.getOffsetStart(), n.getOffsetEnd());
			Assert.assertEquals(n.getText(), snippet);
		}
		
		for (Node c : n.getChildren()) {
			checkNodeOffsetRecursive(c, content);
		}
	}

	private ParsingResult<Node> parse() {
		return new RecoveringParseRunner<Node>(getParser().InputLine()).run(getInFileContent());		
	}
	
	private String getInFileContent() {
		return FileUtils.readAllText(inFile);
	}

	private String getExpFileContent() {
		return FileUtils.readAllText(expFile).replaceAll("\\r", "");
	}

	private void checkPreconditions(final File f) {
		Assert.assertTrue(String.format("File %s does not exist.", f), f.exists());
		Assert.assertTrue(String.format("File %s can not be read.", f), f.canRead());
	}

	private void buildExpFile() {
		final String filename = inFile.getName().substring(0, inFile.getName().indexOf("."));
		expFile = new File(inFile.getParentFile(), filename + ".exp");
	}
}
