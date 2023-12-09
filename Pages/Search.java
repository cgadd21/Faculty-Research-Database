package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Models.*;
import Services.UserService.*;
import Services.SearchService.*;

public class Search
{
    ISearchService _searchService = new SearchService();

    public Search(IUserService _userService)
    {
        JPanel searchBox = new JPanel(new GridLayout(1,3));

        JLabel lblSearch = new JLabel("Search");
        searchBox.add(lblSearch);
        lblSearch.setFont(new Font("Courier", Font.PLAIN, 32));

        JTextField tfSearch = new JTextField("");
        searchBox.add(tfSearch);
        tfSearch.setFont(new Font("Courier", Font.PLAIN, 32));
        tfSearch.setForeground(Color.BLUE);

        JButton btnAddSearch = new JButton("Add");
        btnAddSearch.setFont(new Font("Courier", Font.PLAIN, 32));
        searchBox.add(btnAddSearch);
        btnAddSearch.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
        {
            _searchService.getSearch().add(tfSearch.getText());
            tfSearch.setText(null);
        }});

        for (String search : _searchService.getSearch())
        {
            JLabel lblInputSearch = new JLabel(search);
            searchBox.add(lblInputSearch);
            lblInputSearch.setFont(new Font("Courier", Font.PLAIN, 32));

            JLabel lblSpace = new JLabel("");
            searchBox.add(lblSpace);
            lblSpace.setFont(new Font("Courier", Font.PLAIN, 32));

            JButton btnRemoveSearch = new JButton("Remove");
            btnRemoveSearch.setFont(new Font("Courier", Font.PLAIN, 32));
            searchBox.add(btnRemoveSearch);
            btnRemoveSearch.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
            {
                _searchService.getSearch().remove(search);
            }});
        }

        JOptionPane.showMessageDialog(null, searchBox,"Search", JOptionPane.QUESTION_MESSAGE);

        _searchService.search(_userService);

        StringBuilder resultsStringBuilder = new StringBuilder();
        for (User user : _searchService.getSearchResults()) 
        {
            resultsStringBuilder.append(user.toSummary()).append("\n ");
        }

        JOptionPane.showMessageDialog(null, resultsStringBuilder,"Results", JOptionPane.INFORMATION_MESSAGE);
    }
}
