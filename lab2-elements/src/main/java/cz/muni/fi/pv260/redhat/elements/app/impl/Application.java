package cz.muni.fi.pv260.redhat.elements.app.impl;

import com.beust.jcommander.Parameter;
import cz.muni.fi.pv260.redhat.elements.app.ElementLibrary;
import cz.muni.fi.pv260.redhat.elements.app.cmd.CommandLine;
import cz.muni.fi.pv260.redhat.elements.app.output.Messages;
import cz.muni.fi.pv260.redhat.elements.app.output.TableFormatter;
import cz.muni.fi.pv260.redhat.elements.app.proxy.ElementLibraryProxy;
import cz.muni.fi.pv260.redhat.elements.csv.CsvParser;
import cz.muni.fi.pv260.redhat.elements.csv.CsvToolkit;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;


/**
 * Application CLI
 */
@SuppressWarnings("FieldMayBeFinal")
public final class Application {

    /**
     * Show usage
     */
    @Parameter(names = "--help", help = true)
    private boolean showUsage = false;

    /**
     * Name
     */
    @Parameter(names = {"--name"}, description = "Element's name")
    private String name;

    /**
     * Symbol
     */
    @Parameter(names = {"-s", "--symbol"}, description = "Element's symbol")
    private String symbol;

    /**
     * Number
     */
    @Parameter(names = {"-n", "--number"}, description = "Element's atomic number")
    private Integer number;

    /**
     * Year
     */
    @Parameter(names = {"-y", "--year"}, description = "Element's year of discovery")
    private Integer year;

    /**
     * Different output format support
     */
    @Parameter(names = {"-f", "--format"}, description = "The output format { plain | csv | json }")
    private String format;

    /**
     * Data
     */
    private ElementLibrary data;
    /**
     * Table Printer
     */
    private final TableFormatter printer;
    /**
     * CLI
     */
    CommandLine cli;

    /**
     * Main
     * @param args the args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // create new Application object
        Application app = new Application();
        // parse arguments
        CommandLine cli = new CommandLine(app);
        cli.parseArguments(args);
        // initialize app with the parsed arguments
        app.initializeCommandLine(cli);
        // run the app or show help
        if (app.showUsage) cli.showUsage(); else  app.run();
    }

    /**
     * Initialize CLI object
     */
    public void initializeCommandLine(CommandLine cli) {
        this.cli = cli;
    }

    private Application() throws IOException {
        URL resource = Application.class.getResource("/elements.csv");
        CsvParser csvParser = CsvToolkit.parser(",", StandardCharsets.UTF_8);

        this.data = new ElementLibraryProxy(csvParser, resource);
        this.printer = new TableFormatter();
    }

    private void run() {
        int usedOptions = 0;
        usedOptions += (number != null) ? 1 : 0;
        usedOptions += (name != null) ? 1 : 0;
        usedOptions += (symbol != null) ? 1 : 0;
        usedOptions += (year != null) ? 1 : 0;

        if (usedOptions != 1)
        {
            System.err.println(Messages.SINGLE_FILTER_REQUIRED);
            cli.showUsage();
            return;
        }

        if (year != null)
        {
            List<Element> elements = data.findByYear(year);
            if (!elements.isEmpty()) {
                System.out.println(printer.format(elements));
            }
            else {
                System.out.println(Messages.No_element_found);
            }
            return;
        } else {

            Optional<Element> element = Optional.empty();
            if (number != null) {
                element = data.findByAtomicNumber(number);
            }
            else if (name != null)  element = data.findByName(name);
            else if (symbol != null) {
                element = data.findBySymbol(symbol);
            }

            element.ifPresentOrElse(
                    (e) -> System.out.println(printer.format(e)),
                    () -> System.out.println(Messages.No_element_found)
            );
        }
    }

}
