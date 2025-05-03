package com.copilot.assignment.dataaccess.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ArticleType {
    TEXT("text"),AUDIO("audio"),VIDEO("video");
    private final String type;
}
