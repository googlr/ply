package net.ocheyedan.ply.script;

import net.ocheyedan.ply.FileUtil;
import net.ocheyedan.ply.Output;
import net.ocheyedan.ply.dep.DependencyAtom;
import net.ocheyedan.ply.dep.Deps;
import net.ocheyedan.ply.props.Context;
import net.ocheyedan.ply.props.PropFile;
import net.ocheyedan.ply.props.Props;

import java.io.File;

/**
 * User: blangel
 * Date: 12/1/11
 * Time: 7:47 PM
 *
 * Prints the project's classpath.  This script assumes the project has been built and all dependencies
 * resolved.
 */
public class PrintClasspathScript {

    public static void main(String[] args) {
        String buildDir = Props.get("build.dir", Context.named("project")).value();
        DependencyAtom dependencyAtom = Deps.getProjectDep();
        String artifactName = dependencyAtom.getArtifactName();
        File artifactFile = FileUtil.fromParts(buildDir, artifactName);
        if (!artifactFile.exists()) {
            Output.print("Packaged artifact [ %s ] not found, run `ply compile package` first.", artifactName);
            System.exit(1);
        }
        String classpath = createClasspath(artifactFile.getPath(), Deps.getResolvedProperties(false));
        Output.print(classpath);
    }

    /**
     * Concatenates together {@code artifact} with the keys of {@code dependencies} (if any), separating each
     * by the {@link File#pathSeparator}.
     * @param artifact of the project
     * @param dependencies of the project, if any
     * @return the concatenated classpath
     */
    private static String createClasspath(String artifact, PropFile dependencies) {
        StringBuilder buffer = new StringBuilder(artifact);
        for (PropFile.Prop dependency : dependencies.props()) {
            buffer.append(File.pathSeparator);
            buffer.append(dependency.value());
        }
        return buffer.toString();
    }

}
