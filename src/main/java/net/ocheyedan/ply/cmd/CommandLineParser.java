package net.ocheyedan.ply.cmd;

import net.ocheyedan.ply.Iter;
import net.ocheyedan.ply.cmd.build.Build;
import net.ocheyedan.ply.cmd.config.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: blangel
 * Date: 12/22/11
 * Time: 11:03 AM
 *
 * Responsible for parsing the command line arguments given to Ply and creating a Command object.
 */
public final class CommandLineParser {

    /**
     * A singleton, empty {@link Args} object.
     */
    final static Args NIL = new Args(Collections.<String>emptyList(), Collections.<String>emptyList());

    /**
     * @param args to parse
     * @return a {@link Command} parsed from {@code args}.
     */
    public static Command parse(String[] args) {
        if ((args == null) || (args.length < 1) || "--usage".equals(args[0]) || "--help".equals(args[0])) {
            return new Usage(parseArgs(Iter.sized(args)));
        } else if ("--version".equals(args[0]) || "-v".equals(args[0]) || "-version".equals(args[0])) {
            return new Version(parseArgs(Iter.sized(args)));
        } else if ("init".equals(args[0])) {
            return new Init(parseArgs(Iter.sized(args)));
        } else if ("get".equals(args[0]) || args[0].endsWith(":get")) {
            return new Get(parseArgs(Iter.sized(args)));
        } else if ("get-all".equals(args[0]) || args[0].endsWith(":get-all")) {
            return new GetAll(parseArgs(Iter.sized(args)));
        } else if ("set".equals(args[0]) || args[0].endsWith(":set")) {
            return new Set(parseArgs(Iter.sized(args)));
        } else if ("rm".equals(args[0]) || args[0].endsWith(":rm")) {
            return new Remove(parseArgs(Iter.sized(args)));
        } else if ("append".equals(args[0]) || args[0].endsWith(":append")) {
            return new Append(parseArgs(Iter.sized(args)));
        } else if ("prepend".equals(args[0]) || args[0].endsWith(":prepend")) {
            return new Prepend(parseArgs(Iter.sized(args)));
        } else if ("update".equals(args[0]) || args[0].endsWith(":update")) { 
            return new Update(parseArgs(Iter.sized(args)));
        } else if ("describe".equals(args[0]) || args[0].endsWith(":describe")) {
            return new Describe(parseArgs(Iter.sized(args)));
        } else {
            return new Build(parseArgs(Iter.sized(args)));
        }
    }

    public static Args parseArgs(Iter.Sized<String> clArgs) {
        if ((clArgs == null) || (clArgs.size() < 1)) {
            return NIL;
        }
        List<String> args = new ArrayList<String>(clArgs.size());
        List<String> adHocProps = new ArrayList<String>(2);
        for (String arg : clArgs) {
            if (arg.startsWith("-P")) {
                if (arg.length() > 2) {
                    adHocProps.add(arg.substring(2));
                }
            } else {
                args.add(arg);
            }
        }
        return new Args(args, adHocProps);
    }

}