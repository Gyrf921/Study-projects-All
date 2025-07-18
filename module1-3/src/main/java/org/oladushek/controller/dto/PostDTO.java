package org.oladushek.controller.dto;

import org.oladushek.model.Label;

import java.util.List;

public record PostDTO (Long id, String title, String content, List<Label> postLabels) {
}
