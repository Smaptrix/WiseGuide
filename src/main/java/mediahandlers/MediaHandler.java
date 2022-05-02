/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:         Joe Ingham
    Date Created:   01/03/2022
    Last Updated:   07/03/2022
 */

package mediahandlers;

import java.io.File;

public abstract class MediaHandler {


    File filePath;

    public void setFilePath(File filePath){

        this.filePath = filePath;

    }


}
