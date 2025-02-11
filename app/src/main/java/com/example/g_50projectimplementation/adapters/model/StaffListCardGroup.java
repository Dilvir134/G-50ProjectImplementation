package com.example.g_50projectimplementation.adapters.model;

import java.util.List;

public class StaffListCardGroup {
    private String groupTitle;
    private List<StaffListCard> cards;

    public StaffListCardGroup(String groupTitle, List<StaffListCard> cards) {
        this.groupTitle = groupTitle;
        this.cards = cards;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public List<StaffListCard> getCards() {
        return cards;
    }
}
