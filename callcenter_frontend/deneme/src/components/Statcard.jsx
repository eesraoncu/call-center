const StatCard = ({ title, value }) => {
    return (
        <div className="anasayfa-card">
            <h3 className="anasayfa-card-title">{title}</h3>
            <p className="anasayfa-number">{value}</p>
        </div>
    );
};

export default StatCard;
