package Services.FileService;

import java.io.*;
import javax.swing.*;

public class FileService implements IFileService
{
    @Override
    public String getFile()
    {
        try
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a File");
            StringBuilder abstractContent = new StringBuilder();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                String line;

                while ((line = reader.readLine()) != null) 
                {
                    abstractContent.append(line).append("\n");
                }
            }
            return abstractContent.toString();
        }
        catch (Exception e) 
        {
            return null;
        }
    }
}
