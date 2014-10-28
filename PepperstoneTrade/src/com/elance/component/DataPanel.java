package com.elance.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.elance.nj4x.MT4ConnectionUtil;
import com.elance.util.constants.ComponentConstants;
import com.elance.vo.AccountVO;
import com.jfx.SelectionPool;
import com.jfx.SelectionType;
import com.jfx.strategy.OrderInfo;

public class DataPanel extends JPanel {

	private static final long serialVersionUID = -8610183526601441962L;
	
	private JPanel tabPanel;
	private JPanel buttonPanel;
	
	private List<AccountVO> accountList;
	
	public DataPanel(){
	}
	
	public DataPanel(List<AccountVO> accountList){
		this.accountList=accountList;
	}

	public void initTabPanel() {
		
        
        tabPanel=new JPanel();
        buttonPanel=new JPanel();
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
		c.ipadx=800;
		c.ipady=600;
		tabPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Accounts information"));
		add(tabPanel,c);
		c.gridx=0;
		c.gridy=1;
		c.ipady=40;
		add(buttonPanel,c);
        
        tabPanel.setLayout(null);
        buttonPanel.setLayout(null);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(2, 20,795, 575);
        
        int index=0;
        String account=null;
        JPanel panel=null;
        for(AccountVO accountVO:accountList){
            account=accountVO.getAccountText().getText();
        	panel = makeTextPanel(accountVO);
            tabbedPane.addTab(account, null, panel,"Data of "+account);
            if(accountVO.isLoginResult()){
            	tabbedPane.setForegroundAt(index,Color.BLUE);
            }else{
            	tabbedPane.setForegroundAt(index,Color.RED);
            }
            index++;
        }      
        
        //Add the tabbed pane to this panel.
        tabPanel.add(tabbedPane);
        
        JButton openBuyButton=new JButton("Open Buy");
        openBuyButton.setBounds(2, 5, 100, 25);
        buttonPanel.add(openBuyButton);
        
        JButton openSellButton=new JButton("Open Sell");
        openSellButton.setBounds(110, 5, 100, 25);
        buttonPanel.add(openSellButton);
        
        JButton closeButton=new JButton("Close");
        closeButton.setBounds(218, 5, 100, 25);
        buttonPanel.add(closeButton);
        
        JButton hedgeButton=new JButton("Hedge");
        hedgeButton.setBounds(325, 5, 100, 25);
        buttonPanel.add(hedgeButton);
        
        JButton closeHedgeButton=new JButton("Close Hedge");
        closeHedgeButton.setBounds(432, 5, 120, 25);
        buttonPanel.add(closeHedgeButton);
        
        JButton loginAgainButton=new JButton("Login Again");
        loginAgainButton.setBounds(677, 5, 120, 25);
        buttonPanel.add(loginAgainButton);
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    
    protected JPanel makeTextPanel(AccountVO accountVO) {
    	
    	JPanel panel = new JPanel(false);
    	
    	boolean loginSuccess=accountVO.isLoginResult();
    	String tabContent=null;
    	
    	if(loginSuccess){
    
    		MT4ConnectionUtil mt4Uitl=accountVO.getMt4ConnectionUtil();
    		
    		tabContent="Data of "+accountVO.getAccountText().getText();
    		panel.setLayout(null);
    		
    		JLabel serverConnectTimeLabel=new JLabel("Server connect time: ");
        	JLabel serverConnectTimeValue = new JLabel(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accountVO.getConnectTime()));
        	serverConnectTimeLabel.setBounds(20, 10, 150,ComponentConstants.COMPONENT_HEIGHT);
        	serverConnectTimeValue.setBounds(150,10, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
        	panel.add(serverConnectTimeLabel);
        	panel.add(serverConnectTimeValue);
        	
        	JLabel currencyEquityLabel=new JLabel("Currency equity: ");
        	JLabel currencyEquityValue = new JLabel(String.valueOf(mt4Uitl.accountEquity()));
        	currencyEquityLabel.setBounds(47, 30, 130,ComponentConstants.COMPONENT_HEIGHT);
        	currencyEquityValue.setBounds(150,30, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
        	panel.add(currencyEquityLabel);
        	panel.add(currencyEquityValue);
        	
    		JLabel accountBalanceLabel=new JLabel("Account Balance: ");
    		JLabel accountBalanceValue = new JLabel(String.valueOf(mt4Uitl.accountBalance()));
    		accountBalanceLabel.setBounds(40, 50, 130,ComponentConstants.COMPONENT_HEIGHT);
    		accountBalanceValue.setBounds(150,50, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
    		panel.add(accountBalanceLabel);
    		panel.add(accountBalanceValue);
    		
    		JLabel lotSizeForNextTradeLabel=new JLabel("Lot size for next trade:");
    		JLabel lotSizeForNextTradeValue = new JLabel("");
    		lotSizeForNextTradeLabel.setBounds(10, 70, 150,ComponentConstants.COMPONENT_HEIGHT);
    		lotSizeForNextTradeValue.setBounds(150,70, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
    		panel.add(lotSizeForNextTradeLabel);
    		panel.add(lotSizeForNextTradeValue);
    		
    		JLabel openTradeLotsLabel=new JLabel("Open trade lots:");
    		JLabel openTradeLotsValue = new JLabel("");
    		openTradeLotsLabel.setBounds(47, 90, 150,ComponentConstants.COMPONENT_HEIGHT);
    		openTradeLotsValue.setBounds(150,90, ComponentConstants.COMPONENT_LABEL_WIDTH_MEDIUM,ComponentConstants.COMPONENT_HEIGHT);
    		panel.add(openTradeLotsLabel);
    		panel.add(openTradeLotsValue);
    		
    		JLabel currencyPairsLabel=new JLabel("Currency paris:");
    		currencyPairsLabel.setBounds(50,110, 130, ComponentConstants.COMPONENT_HEIGHT);
    		List<String> currencyPairList=mt4Uitl.getSymbols();
    	    String[] currencyPairs=currencyPairList.toArray(new String[currencyPairList.size()]);
    		JComboBox<String> currencyPairsCombox=new JComboBox<String>(currencyPairs);
    	    currencyPairsCombox.setBounds(150, 110, 150, ComponentConstants.COMPONENT_HEIGHT);
    	    panel.add(currencyPairsCombox);
    	    panel.add(currencyPairsLabel);
    		
    	    JPanel tablePanel=new JPanel();
    	    String[] columnNames={"Order","Time","Symbol","Type","Price","Profit"};
    	    DateFormat format=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    		int availableOrdersCount=mt4Uitl.ordersTotal();
    		Object[][] cells=new Object[availableOrdersCount][6];
    		OrderInfo orderInfo=null;
    		for(int i=0;i<availableOrdersCount;i++){
    			orderInfo =mt4Uitl.orderGet(i, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES);
    			cells[i][0]=orderInfo.ticket();
    			cells[i][1]=format.format(orderInfo.getOpenTime());
    			cells[i][2]=orderInfo.getSymbol();
    			cells[i][3]=orderInfo.getType();
    			cells[i][4]=orderInfo.getOpenPrice();
    			cells[i][5]=orderInfo.getProfit();
    		}
    		JTable jTable=new JTable(cells,columnNames);
    		jTable.setPreferredScrollableViewportSize(new Dimension(750, 360));
    		JScrollPane sPane=new JScrollPane(jTable);
    		tablePanel.setBounds(5, 135, 780, 410);
    		tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Orders List"));
    		tablePanel.add(sPane);
    		panel.add(tablePanel);
    		

    		JLabel profiltLabel=new JLabel("Account Profit: ");
    		JLabel profitValue = new JLabel(String.valueOf(mt4Uitl.accountProfit()));
    		profiltLabel.setBounds(410, 10, 150,ComponentConstants.COMPONENT_HEIGHT);
    		profitValue.setBounds(500,10, 150,ComponentConstants.COMPONENT_HEIGHT);
    		panel.add(profiltLabel);
    		panel.add(profitValue);
    		
    		JLabel marginLabel=new JLabel("Margin: ");
        	JLabel marginValue = new JLabel(String.valueOf(mt4Uitl.accountMargin()));
        	marginLabel.setBounds(452, 30, 150,ComponentConstants.COMPONENT_HEIGHT);
        	marginValue.setBounds(500,30, 150,ComponentConstants.COMPONENT_HEIGHT);
        	panel.add(marginLabel);
        	panel.add(marginValue);
        	
        	JLabel freeMarginLabel=new JLabel("Free Margin: ");
        	JLabel freeMarginValue = new JLabel(String.valueOf(mt4Uitl.accountFreeMargin()));
        	freeMarginLabel.setBounds(425, 50, 150,ComponentConstants.COMPONENT_HEIGHT);
        	freeMarginValue.setBounds(500,50, 150,ComponentConstants.COMPONENT_HEIGHT);
        	panel.add(freeMarginLabel);
        	panel.add(freeMarginValue);
    		
    	}else{
    		tabContent=accountVO.getErrorMessage();
    		panel.setLayout(new GridLayout(1, 1));
            JLabel filler = new JLabel(tabContent);
            filler.setHorizontalAlignment(JLabel.CENTER);
            panel.add(filler);
    	}
        return panel;
    }
    
}
