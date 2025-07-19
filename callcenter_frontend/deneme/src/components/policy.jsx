import React, { useState } from 'react';
import './css/policy.css';

function Policy({ isOpen, onClose, onAccept }) {
    const [isChecked, setIsChecked] = useState(false);
    const [showText, setShowText] = useState(false);


    if (!isOpen) return null;
    const handleAccept = () => {
        if (!isChecked) {
            alert('Lütfen gizlilik sözleşmesini onaylayınız!');
            return;
        }
        onAccept(); // Sadece onaylandığında çalışır
    };

    return (
        <div className="pol">
            <div onClick={onClose} className="overlay"></div>
            <div className="pol-content">
                <h2>Gizlilik Sözleşmesi</h2>
                <div className="policy-container">
                    {/* Sol Tarafta Kısa Açıklama */}
                    <div
                        className="policy-trigger"
                        onClick={() => setShowText(!showText)}
                    >
                        <p>Gizlilik sözleşmesi için buraya tıklayınız...</p>
                    </div>


                    {/* Sağ Tarafta Detaylı Metin */}
                    {showText && (
                        <div className="policy-details">
                            <h3>Gizlilik Politikası Detayları:
                                Bu Gizlilik Politikası, AuraCall Company olarak hizmetlerimizi kullandığınızda topladığımız, kullandığımız, açıkladığımız ve koruduğumuz kişisel verilerinizi açıklamaktadır. Gizliliğiniz bizim için son derece önemlidir ve verilerinizin nasıl işlendiği konusunda şeffaf olmak istiyoruz.
                            </h3>
                            <embed
                                src="/GizlilikPolitikasi.pdf"
                                type="application/pdf"
                                width="100%"
                                height="500px"
                            />
                        </div>
                    )}
                </div>


                <div className="policy-actions">
                    <div className="policy-checkbox">
                        <input
                            type="checkbox"
                            id="policyCheck"
                            checked={isChecked}
                            onChange={(e) => setIsChecked(e.target.checked)}
                        />
                        <label htmlFor="policyCheck">
                            Gizlilik sözleşmesini okudum ve kabul ediyorum
                        </label>
                    </div>

                    <div className="policy-buttons">
                        <button className="close-button" onClick={onClose}>
                            <span aria-hidden="true">&times;</span> Kapat
                        </button>
                        <button className="accept-button"
                            onClick={handleAccept}
                            disabled={!isChecked} >
                            Onaylıyorum
                        </button>
                    </div>
                </div>


            </div>
        </div>
    );
}

export default Policy; 