package com.test.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;


@Slf4j
public class BoardMap extends BaseComponent {

    public BoardMap(SelenideElement locator){
        super(locator);
    }

    //battlefield battlefield__self ||| battlefield battlefield__rival battlefield__wait
    //battlefield-cell battlefield-cell__hit battlefield-cell__done
    //battlefield-cell battlefield-cell__miss
    //battlefield-cell battlefield-cell__empty
    //battlefield-cell battlefield-cell__empty battlefield-cell__undiscovered

    //ship-types
    //ship ship__killed

    public SelenideElement getCellByTableAndCoordinates(int user, int rowNumber, int colNumber){
        log.debug("Get table");
        StringBuilder tableLocator = new StringBuilder();
        tableLocator.append("(//tbody)[")
                .append(user)
                .append("]//tr[@class='battlefield-row']//td[")
                .append(colNumber)
                .append("]");
        log.debug("Show table locator: {}", tableLocator);

        //ElementsCollection array = locator.findAll(By.xpath("(//tbody)[1]//tr[@class='battlefield-row']"));
        ElementsCollection array = locator.findAll(By.xpath(tableLocator.toString()));
        TableRow row = new TableRow(array.get(rowNumber -1));

        log.debug("Return cell: {}", row.getLocator());

        //return row.find(By.xpath("(//td)[" + colNumber + "]"));
        //return $("(//tbody)[1]//tr[@class='battlefield-row']//td[1]").find("td", colNumber);
        return row.getLocator();
    }

}
