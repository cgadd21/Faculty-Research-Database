package Services.FileService;

import java.io.*;
import javax.swing.*;

public class FileService implements IFileService
{
    private String fileContent;

    @Override
    public String getFileContent()
    {
        return fileContent;
    }

    @Override
    public void getFile()
    {
        try
        {
            fileContent = null;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a File");
            StringBuilder abstractContent = new StringBuilder();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                while (reader.readLine() != null) 
                {
                    abstractContent.append(reader.readLine()).append("\n");
                }
            }
            fileContent = abstractContent.toString();
        }
        catch (Exception e) {}
    }
}
