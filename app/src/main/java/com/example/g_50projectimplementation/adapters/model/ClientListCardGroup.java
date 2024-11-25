package com.example.g_50projectimplementation.adapters.model;

import java.util.List;

public class ClientListCardGroup {
    private String groupTitle;
    private List<ClientListCard> cards;

    public ClientListCardGroup(String groupTitle, List<ClientListCard> cards) {
        this.groupTitle = groupTitle;
        this.cards = cards;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public List<ClientListCard> getCards() {
        return cards;
    }
}
