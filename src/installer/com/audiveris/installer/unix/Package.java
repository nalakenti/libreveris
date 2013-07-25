//----------------------------------------------------------------------------//
//                                                                            //
//                                P a c k a g e                               //
//                                                                            //
//----------------------------------------------------------------------------//
// <editor-fold defaultstate="collapsed" desc="hdr">                          //
//  Copyright © Herve Bitteur and others 2000-2013. All rights reserved.      //
//  This software is released under the GNU General Public License.           //
//  Goto http://kenai.com/projects/audiveris to report bugs or suggestions.   //
//----------------------------------------------------------------------------//
// </editor-fold>
package com.audiveris.installer.unix;

import com.audiveris.installer.RegexUtil;
import com.audiveris.installer.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class {@code Package} handles a Linux package requirement and
 * installation, using the underlying "apt" and dpkg" utilities.
 *
 * @author Hervé Bitteur
 */
public class Package
{
    //~ Static fields/initializers ---------------------------------------------

    /**
     * Usual logger utility
     */
    private static final Logger logger = LoggerFactory.getLogger(Package.class);
    //~ Instance fields --------------------------------------------------------

    /**
     * Name of the package.
     */
    public final String name;

    /**
     * Minimum version required.
     */
    public final VersionNumber minVersion;

    //~ Constructors -----------------------------------------------------------
    //---------//
    // Package //
    //---------//
    /**
     * Create a Package instance.
     *
     * @param name       the package name
     * @param minVersion the minimum required version
     */
    public Package (String name,
                    String minVersion)
    {
        if ((name == null) || name.isEmpty()) {
            throw new IllegalArgumentException("Null or empty package name");
        }

        if ((minVersion == null) || minVersion.isEmpty()) {
            throw new IllegalArgumentException(
                    "Null or empty package minimum version");
        }

        this.name = name;
        this.minVersion = new VersionNumber(minVersion);
    }

    //~ Methods ----------------------------------------------------------------
    //---------------------//
    // getInstalledVersion //
    //---------------------//
    /**
     * Report the version number of this package, if installed.
     *
     * @return the version number, or null if not found
     */
    public VersionNumber getInstalledVersion ()
    {
        final String VERSION = "version";
        final Pattern versionPattern = Pattern.compile(
                "^Version: " + RegexUtil.group(VERSION, ".*") + "$");

        try {
            List<String> output = new ArrayList<String>();
            int res = Utilities.runProcess("bash", output, "-c", "dpkg -s " + name);
            if (res != 0) {
                // Using command 'dpkg -s', we get a non-zero exit code
                // when package is not installed, so let's simply return null.
//                final String lines = Utilities.dumpOfLines(output);
//                logger.warn(lines);
                return null;
            } else {
                for (String line : output) {
                    Matcher matcher = versionPattern.matcher(line);

                    if (matcher.matches()) {
                        String versionStr = RegexUtil.getGroup(matcher, VERSION);

                        return new VersionNumber(versionStr);
                    }
                }
            }
        } catch (Exception ex) {
            logger.warn("Could not get package version", ex);
        }

        return null;
    }

    //---------//
    // install //
    //---------//
    /**
     * Install the latest version of this package
     *
     * @throws Exception
     */
    public void install ()
            throws Exception
    {
        try {
            List<String> output = new ArrayList<String>();
            int res = Utilities.runProcess(
                    "bash",
                    output,
                    "-c",
                    "apt-get install -y " + name);
            if (res != 0) {
                final String lines = Utilities.dumpOfLines(output);
                logger.warn(lines);
                throw new RuntimeException("Error installing package " + name
                                           + " exit: " + res + "\n" + lines);
            }
        } catch (Exception ex) {
            logger.warn("Error in running apt-get", ex);
            throw ex;
        }
    }

    //-------------//
    // isInstalled //
    //-------------//
    public boolean isInstalled ()
    {
        VersionNumber installedVersion = getInstalledVersion();

        if (installedVersion == null) {
            return false;
        }

        return installedVersion.compareTo(minVersion) >= 0;
    }
}
