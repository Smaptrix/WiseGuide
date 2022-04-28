/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   01/03/2022
    Last Updated:   10/03/2022
 */

package mediahandlers;

import java.io.File;


/**
 * MediaHandler class
 */
public abstract class MediaHandler {

    /**
     * Variable of File type to store the file path of the media.
     */
    File filePath;

    /**
     * <p>
     *     setFilePath method sets the filepath to the desired media's filepath.
     * </p>
     * @param filePath is the desired filepath for the file.
     */
    public void setFilePath(File filePath){

        this.filePath = filePath;

    }


}
