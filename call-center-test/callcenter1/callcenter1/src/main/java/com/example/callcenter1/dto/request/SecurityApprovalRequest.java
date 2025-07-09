package com.example.callcenter1.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SecurityApprovalRequest {
    @NotNull(message = "Onay durumu boş olamaz")
    private Boolean approved;

    // Getter ve Setter'lar (Lombok @Data ile otomatik oluşur ama manuel versiyonu)
    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}