//----------------------------------------------------------------------------//
//                                                                            //
//                            D e s c r i p t o r                             //
//                                                                            //
//----------------------------------------------------------------------------//
// <editor-fold defaultstate="collapsed" desc="hdr">                          //
//  Copyright © Hervé Bitteur and others 2000-2013. All rights reserved.      //
//  This software is released under the GNU General Public License.           //
//  Goto http://kenai.com/projects/audiveris to report bugs or suggestions.   //
//----------------------------------------------------------------------------//
// </editor-fold>
package com.audiveris.installer;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Interface {@code Descriptor} defines the features to be provided
 * to the Installer by any target environment (os + arch).
 *
 * <p>This interface has been defined with generality in mind, but may have
 * suffered from the bias of the initial Windows-based implementation.
 * Hence, it still may evolve when we hit the development of implementations for
 * Unix and for Mac.
 *
 * @author Hervé Bitteur
 */
public interface Descriptor
{
    //~ Static fields/initializers ---------------------------------------------

    /** ID for company developing audiveris software. */
    static final String COMPANY_ID = "AudiverisLtd";

    /** Name for audiveris program. */
    static final String TOOL_NAME = "audiveris";

    /** Name of environment variable for tessdata parent. */
    static final String TESSDATA_PREFIX = "TESSDATA_PREFIX";

    /** Name of Tesseract folder. */
    static final String TESSERACT_OCR = "tesseract-ocr";

    /** Name of tessdata folder. */
    static final String TESSDATA = "tessdata";

    /** Minimum suitable version for Ghostscript. */
    static final String GHOSTSCRIPT_MIN_VERSION = "9.06";

    //~ Methods ----------------------------------------------------------------
    /**
     * Report the folder to be used for read-write configuration.
     *
     * @return the configuration folder
     */
    File getConfigFolder ();

    /**
     * Report the shell command to copy recursively source to target
     *
     * @param source path of source folder
     * @param target path of target folder
     * @return the proper shell command
     */
    String getCopyCommand (Path source,
                           Path target);

    /**
     * Report the folder to be used for read-write data.
     *
     * @return the data folder
     */
    File getDataFolder ();

    /**
     * Report the folder to be used for Tesseract-OCR if not yet set.
     * If Tesseract application is installed, this folder is pointed by the
     * environment variable TESSDATA_PREFIX, and this method is not called.
     * If Tesseract application is not installed (and actually Audiveris does
     * not need the application, but just the language files) then we call this
     * method to know where we have to store the Tesseract trained data files.
     *
     * @return the parent folder of tessdata
     */
    File getDefaultTessdataPrefix ();

    /**
     * Report the shell command to delete a file
     *
     * @param file path of file to delete
     * @return the proper shell command
     */
    String getDeleteCommand (Path file);

    /**
     * Report the shell command to create directories
     *
     * @param dir path to final folder
     * @return the proper shell command
     */
    String getMkdirCommand (Path dir);

    /**
     * Report the shell command to set the executable flag on a file
     *
     * @param file path of file to set
     * @return the proper shell command
     */
    String getSetExecCommand (Path file);

    /**
     * Report the collection of specific files to install
     *
     * @return the references of specific files for the target environment
     */
    List<SpecificFile> getSpecificFiles ();

    /**
     * Report a folder which can be used for temporary files created
     * during installation.
     * This folder is created anew when installation starts, and deleted when
     * installation completes successfully.
     *
     * @return a temporary folder
     */
    File getTempFolder ();

    /**
     * Install the proper C++ runtime.
     */
    void installCpp ()
            throws Exception;

    /**
     * Install a suitable Ghostscript.
     */
    void installGhostscript ()
            throws Exception;

    /**
     * Install a suitable Tesseract.
     */
    void installTesseract ()
            throws Exception;

    /**
     * Report whether the current process is run with administrator
     * privileges.
     *
     * @return true if admin
     */
    boolean isAdmin ();

    /**
     * Check whether the proper C++ runtime is installed.
     *
     * @return true if installed
     */
    boolean isCppInstalled ();

    /**
     * Check whether a suitable Ghostscript is installed.
     *
     * @return true if installed
     */
    boolean isGhostscriptInstalled ();

    /**
     * Check whether a suitable Tesseract is installed.
     *
     * @return true if installed
     */
    boolean isTesseractInstalled ();

    /**
     * Run a shell with admin privilege on the provided commands
     *
     * @param asAdmin  true for elevated
     * @param commands the list of commands to run
     * @return true if OK
     */
    boolean runShell (boolean asAdmin,
                      List<String> commands)
            throws Exception;

    /**
     * Mark the provided file as executable.
     *
     * @param file the file to be set
     */
    void setExecutable (Path file)
            throws Exception;
}
