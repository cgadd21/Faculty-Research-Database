package Pages;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Models.*;
import Services.UserService.*;
import Services.SearchService.*;

public class Search extends JFrame
{
    ISearchService _searchService = new SearchService();

    public Search(IUserService _userService)
    {
        super("Search");
        setSize(630,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jSearch = new JPanel();
        jSearch.setLayout(new GridLayout(1,3));
        add(jSearch);
        setVisible(true);

        JLabel lblSearch = new JLabel("Search");
        JTextField tfSearch = new JTextField("");
        JButton btnAddSearch = new JButton("Add");
        JButton btnSearch = new JButton("Search");

        lblSearch.setFont(new Font("Courier", Font.PLAIN, 32));
        tfSearch.setFont(new Font("Courier", Font.PLAIN, 32));
        btnAddSearch.setFont(new Font("Courier", Font.PLAIN, 32));
        btnSearch.setFont(new Font("Courier", Font.PLAIN, 32));

        tfSearch.setForeground(Color.BLUE);

        jSearch.add(lblSearch);
        jSearch.add(tfSearch);
        jSearch.add(btnAddSearch);

        btnAddSearch.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
        {
            _searchService.getSearch().add(tfSearch.getText());
            tfSearch.setText(null);
            jSearch.removeAll();
            jSearch.setLayout(new GridLayout(_searchService.getSearch().size() + 2, 3));
            jSearch.add(lblSearch);
            jSearch.add(tfSearch);
            jSearch.add(btnAddSearch);
            for (String search : _searchService.getSearch())
            {
                JLabel lblInputSearch = new JLabel(search);
                jSearch.add(lblInputSearch);
                lblInputSearch.setFont(new Font("Courier", Font.PLAIN, 32));
                jSearch.add(new JLabel());
                jSearch.add(new JLabel());
            }
            jSearch.add(new JLabel());
            jSearch.add(btnSearch);
        }});

        btnSearch.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
        {
            _searchService.search(_userService);

            StringBuilder resultsStringBuilder = new StringBuilder();
            for (User user : _searchService.getSearchResults()) 
            {
                resultsStringBuilder.append(user.toSummary()).append("\n ");
            }

            JOptionPane.showMessageDialog(null, resultsStringBuilder,"Results", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
        }});
    }
}
