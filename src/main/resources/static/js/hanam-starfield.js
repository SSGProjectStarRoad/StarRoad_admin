const canvas = document.getElementById('store-area');
const ctx = canvas.getContext('2d');

const stores = [
    {name: '난닝구', x: 285, y: 225, width: 20, height: 20, storeId: 1},
    {name: '캉골', x: 330, y: 217, width: 20, height: 20, storeId: 2},
    {name: '뉴에라', x: 355, y: 245, width: 20, height: 20, storeId: 3},
    {name: '원더플레이스', x: 372, y: 170, width: 20, height: 20, storeId: 0},
    {name: '랩', x: 433, y: 243, width: 20, height: 20, storeId: 0},
    {name: '유니클로', x: 478, y: 180, width: 20, height: 20, storeId: 0},
    {name: '플라잉타이거 코펜하겐', x: 535, y: 230, width: 20, height: 20, storeId: 0},
    {name: '사봉', x: 557, y: 278, width: 20, height: 20, storeId: 0},
    {name: '네스프레소', x: 593, y: 260, width: 20, height: 20, storeId: 0},
    {name: 'Apple', x: 648, y: 200, width: 20, height: 20, storeId: 526},
    {name: '스튜디오 톰보이', x: 706, y: 200, width: 20, height: 20, storeId: 0},
    {name: '폴더', x: 748, y: 250, width: 20, height: 20, storeId: 0},
    {name: '나이키', x: 795, y: 220, width: 20, height: 20, storeId: 524},
    {name: '에이랜드', x: 844, y: 260, width: 20, height: 20, storeId: 0},
    {name: '캠퍼', x: 876, y: 275, width: 20, height: 20, storeId: 0},
    {name: '젠틀몬스터', x: 916, y: 220, width: 20, height: 20, storeId: 0},
    {name: '탬버린즈', x: 962, y: 260, width: 20, height: 20, storeId: 0},
    {name: '자주', x: 1003, y: 220, width: 20, height: 20, storeId: 523},
    {name: '일렉트로마트', x: 1110, y: 180, width: 20, height: 20, storeId: 0},

    {name: '빈브라더스', x: 282, y: 380, width: 20, height: 20, storeId: 520},
    {name: '이솝', x: 315, y: 370, width: 20, height: 20, storeId: 0},
    {name: '로사케이', x: 322, y: 397, width: 20, height: 20, storeId: 0},
    {name: '록시땅', x: 375, y: 360, width: 20, height: 20, storeId: 0},
    {name: '클라베드제이', x: 385, y: 383, width: 20, height: 20, storeId: 0},
    {name: '플리츠미', x: 385, y: 404, width: 20, height: 20, storeId: 0},
    {name: '마리떼프랑소와 저버', x: 405, y: 343, width: 20, height: 20, storeId: 0},
    {name: '탑텐', x: 445, y: 400, width: 20, height: 20, storeId: 0},
    {name: '파리 생제르망', x: 460, y: 360, width: 20, height: 20, storeId: 0},
    {name: '슈스파', x: 484, y: 390, width: 20, height: 20, storeId: 0},
    {name: '원더 브라', x: 507, y: 350, width: 20, height: 20, storeId: 0},
    {name: '지오다노', x: 530, y: 405, width: 20, height: 20, storeId: 0},
    {name: '디자인 스킨', x: 556, y: 345, width: 20, height: 20, storeId: 0},
    {name: '아웃도어 프로덕츠', x: 588, y: 377, width: 20, height: 20, storeId: 0},
    {name: '플랙', x: 616, y: 402, width: 20, height: 20, storeId: 0},
    {name: '게스', x: 643, y: 370, width: 20, height: 20, storeId: 0},
    {name: '커버낫', x: 669, y: 400, width: 20, height: 20, storeId: 0},
    {name: '널디', x: 695, y: 380, width: 20, height: 20, storeId: 0},
    {name: '올리브영', x: 740, y: 395, width: 20, height: 20, storeId: 0},
    {name: '쿠에른', x: 761, y: 350, width: 20, height: 20, storeId: 0},
    {name: '더일마', x: 798, y: 380, width: 20, height: 20, storeId: 0},
    {name: '리스트', x: 825, y: 350, width: 20, height: 20, storeId: 0},
    {name: '올스튜디오스', x: 863, y: 405, width: 20, height: 20, storeId: 0},
    {name: '라수아 패밀리', x: 878, y: 350, width: 20, height: 20, storeId: 0},
    {name: '매그제이', x: 900, y: 385, width: 20, height: 20, storeId: 0},
    {name: '나인', x: 932, y: 368, width: 20, height: 20, storeId: 0},
    {name: '러쉬', x: 957, y: 344, width: 20, height: 20, storeId: 529},
    {name: '더바디샵', x: 982, y: 362, width: 20, height: 20, storeId: 530},
    {name: '로우로우', x: 969, y: 383, width: 20, height: 20, storeId: 0},
    {name: '라미', x: 964, y: 405, width: 20, height: 20, storeId: 0},
    {name: '미니골드', x: 1032, y: 400, width: 20, height: 20, storeId: 0},

    {name: '온더 보더', x: 283, y: 470, width: 20, height: 20, storeId: 517},
    {name: '풍월장', x: 335, y: 515, width: 20, height: 20, storeId: 0},
    {name: '레스트 인네이처', x: 346, y: 425, width: 20, height: 20, storeId: 0},
    {name: '이마트24 2호점', x: 352, y: 458, width: 20, height: 20, storeId: 0},
    {name: '아프리카', x: 356, y: 484, width: 20, height: 20, storeId: 0},
    {name: '바나나 시스터즈', x: 390, y: 430, width: 20, height: 20, storeId: 0},
    {name: '슈퍼 말차', x: 388, y: 454, width: 20, height: 20, storeId: 0},
    {name: '뮬라 웨어', x: 411, y: 454, width: 20, height: 20, storeId: 0},
    {name: '카웨', x: 432, y: 430, width: 20, height: 20, storeId: 0},
    {name: '스케쳐스', x: 455, y: 460, width: 20, height: 20, storeId: 0},
    {name: '반스', x: 483, y: 440, width: 20, height: 20, storeId: 0},
    {name: '코닥', x: 511, y: 495, width: 20, height: 20, storeId: 0},
    {name: '디스이즈 네버댓', x: 543, y: 490, width: 20, height: 20, storeId: 0},
    {name: '엄브로', x: 590, y: 495, width: 20, height: 20, storeId: 0},
    {name: 'MLB', x: 623, y: 470, width: 20, height: 20, storeId: 0},
    {name: '디스 커버리', x: 663, y: 480, width: 20, height: 20, storeId: 525},
    {name: '파타 고니아', x: 695, y: 455, width: 20, height: 20, storeId: 0},
    {name: '네셔널 지오그래픽', x: 720, y: 510, width: 20, height: 20, storeId: 0},
    {name: '써스데이 아일랜드', x: 755, y: 480, width: 20, height: 20, storeId: 0},
    {name: '스와치', x: 765, y: 505, width: 20, height: 20, storeId: 0},
    {name: '찰스앤 키스', x: 800, y: 508, width: 20, height: 20, storeId: 0},
    {name: '쉬즈미스', x: 827, y: 478, width: 20, height: 20, storeId: 0},
    {name: '현대모터 스튜디오', x: 864, y: 450, width: 20, height: 20, storeId: 0},
    {name: '나우그림', x: 927, y: 435, width: 20, height: 20, storeId: 0},
    {name: '닥터마린', x: 917, y: 463, width: 20, height: 20, storeId: 528},
    {name: '솔브', x: 914, y: 482, width: 20, height: 20, storeId: 0},
    {name: '은가비', x: 905, y: 505, width: 20, height: 20, storeId: 0},
    {name: '알지오지아', x: 1027, y: 420, width: 20, height: 20, storeId: 0},
    {name: '프로젝트엠', x: 1026, y: 445, width: 20, height: 20, storeId: 0},
    {name: '마인드브릿지', x: 1022, y: 470, width: 20, height: 20, storeId: 0},
    {name: 'H&M', x: 1110, y: 445, width: 20, height: 20, storeId: 522},

    {name: '리프사운드', x: 315, y: 615, width: 20, height: 20, storeId: 0},
    {name: '이속우화 구우몽', x: 405, y: 590, width: 20, height: 20, storeId: 0},
    {name: '딤타오', x: 432, y: 620, width: 20, height: 20, storeId: 0},
    {name: '에베 레스트', x: 468, y: 595, width: 20, height: 20, storeId: 0},
    {name: '이비티', x: 508, y: 615, width: 20, height: 20, storeId: 0},
    {name: '일일향', x: 547, y: 630, width: 20, height: 20, storeId: 0},
    {name: '공차', x: 561, y: 577, width: 20, height: 20, storeId: 0},
    {name: '젤라띠 젤라띠', x: 588, y: 598, width: 20, height: 20, storeId: 0},
    {name: '띤띤 드타마린드', x: 597, y: 645, width: 20, height: 20, storeId: 0},
    {name: '스타버그 티바나', x: 640, y: 615, width: 20, height: 20, storeId: 519},
    {name: '노티드', x: 758, y: 580, width: 20, height: 20, storeId: 0},
    {name: '안스베이커리', x: 745, y: 630, width: 20, height: 20, storeId: 518},
    {name: '한우리', x: 812, y: 610, width: 20, height: 20, storeId: 0},
    {name: '광화문 미진', x: 853, y: 588, width: 20, height: 20, storeId: 0},
    {name: '소호정', x: 880, y: 610, width: 20, height: 20, storeId: 521},
    {name: '긴다코', x: 910, y: 565, width: 20, height: 20, storeId: 0},
    {name: '훠궈야', x: 920, y: 645, width: 20, height: 20, storeId: 0},
    {name: '오렌즈', x: 980, y: 568, width: 20, height: 20, storeId: 0},
    {name: '프레시가든', x: 990, y: 620, width: 20, height: 20, storeId: 0},
    {name: '이마트24 1호점', x: 1010, y: 652, width: 20, height: 20, storeId: 0},
    {name: '몰리스펫샵', x: 1100, y: 615, width: 20, height: 20, storeId: 0},
];

const backgroundImage = new Image();
backgroundImage.src = 'https://starfield.co.kr/images/hanam/floor/1F.svg';

backgroundImage.onload = () => {
    ctx.drawImage(backgroundImage, 0, 0, canvas.width, canvas.height);
    stores.forEach(store => drawStoreArea(store));
};

function drawStoreArea(store) {
    const {name, x, y, width, height} = store;
    const textX = store.textX || x;
    const textY = store.textY || y;

    ctx.fillStyle = 'rgba(255,255,255,0)';
    ctx.fillRect(x, y, width, height);

    let fontColor = 'black';
    if (store.storeId != 0) {
        storeReviewColor(store.storeId).then(confidence => {
            switch (confidence == null ? 'UNKNOWN' : confidence) {
                case 'POSITIVE':
                    fontColor = 'blue';
                    break;
                case 'NEGATIVE':
                    fontColor = 'red';
                    break;
                case 'NEUTRAL':
                    fontColor = 'yellow';
                    break;
                default:
                    fontColor = 'black';
            }
            drawStoreName(name, textX, textY, width, height, fontColor);
        }).catch(error => {
            drawStoreName(name, textX, textY, width, height, fontColor);
            console.error('Error fetching data:', error)
        });
    } else {
        drawStoreName(name, textX, textY, width, height, fontColor);
    }
}

function drawStoreName(name, textX, textY, width, height, fontColor) {
    ctx.fillStyle = fontColor;
    ctx.font = '12px Arial';
    const splitName = name.split(' ');
    if (splitName.length < 2) {
        const textWidth = ctx.measureText(name).width;
        ctx.fillText(name, textX + (width - textWidth) / 2, textY + (height + 16) / 2);
    } else {
        const [part1, part2] = splitName;
        const part1Width = ctx.measureText(part1).width;
        const part2Width = ctx.measureText(part2).width;
        ctx.fillText(part1, textX + (width - part1Width) / 2, textY + 8);
        ctx.fillText(part2, textX + (width - part2Width) / 2, textY + 20);
    }
}

canvas.addEventListener('click', (event) => {
    const rect = canvas.getBoundingClientRect();
    const scaleX = canvas.width / rect.width;
    const scaleY = canvas.height / rect.height;
    const clickX = (event.clientX - rect.left) * scaleX;
    const clickY = (event.clientY - rect.top) * scaleY;

    const clickedStore = stores.find(store =>
        clickX >= store.x && clickX <= store.x + store.width &&
        clickY >= store.y && clickY <= store.y + store.height
    );

    if (clickedStore) {
        showStoreInfo(clickedStore);
    }
});

function showStoreInfo(store) {
    const storeInfoDiv = document.getElementById('store-info');
    storeInfoDiv.style.display = 'block';
    const storeDescription = document.getElementById('store-description');
    storeDescription.innerHTML = `여기에 <strong>${store.name}</strong> 매장 정보 확인하러하러 가기`;
    storeDescription.innerHTML += `<br><a type="button" class="btn btn-primary btn-sm" href="/store/detail/${store.storeId}">매장 정보 확인</a>`;
    drawChart(store.storeId);
}

function storeReviewColor(storeId) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/store/' + storeId + '/color',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                resolve(data.confidence);
            },
            error: function (xhr, status, error) {
                console.error('Error fetching data:', error);
                reject(error);
            }
        });
    });
}

function drawChart(storeId) {
    $.ajax({
        url: '/chart/store/' + storeId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            const labels = [];
            const totalReviewCount = [];
            const positiveReviewCount = [];
            const negativeReviewCount = [];
            const neutralReviewCount = [];

            data.forEach(item => {
                labels.push(`${item.reviewYear}-${item.reviewMonth}`);
                totalReviewCount.push(item.reviewCount);
                positiveReviewCount.push(item.positiveReviewCount);
                negativeReviewCount.push(item.negativeReviewCount);
                neutralReviewCount.push(item.neutralReviewCount);
            });

            const chartCanvas = document.getElementById('monthlyReview');
            if (chartCanvas) {
                const ctx = chartCanvas.getContext('2d');
                if (window.storeChart) {
                    window.storeChart.destroy();
                }
                window.storeChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels,
                        datasets: [
                            {
                                label: "Positive",
                                data: positiveReviewCount,
                                backgroundColor: "rgba(0, 156, 255, .5)",
                                borderColor: "rgba(0, 156, 255, .5)",
                                borderWidth: 2,
                            },
                            {
                                label: "Negative",
                                data: negativeReviewCount,
                                backgroundColor: "rgba(255, 0, 0, .5)",
                                borderColor: "rgba(255, 0, 0, .5)",
                                borderWidth: 2,
                            },
                            {
                                label: "Neutral",
                                data: neutralReviewCount,
                                backgroundColor: "rgba(255, 255, 0, .5)",
                                borderColor: "rgba(255, 255, 0, .5)",
                                borderWidth: 2,
                            },
                            {
                                label: 'Total',
                                data: totalReviewCount,
                                backgroundColor: "rgba(0, 255, 0, .5)",
                                borderColor: "rgba(0, 255, 0, .5)",
                                borderWidth: 2,
                            }
                        ]
                    }
                });
            } else {
                console.error('monthlyReview 요소를 찾을 수 없습니다.');
            }
        },
        error: function (xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}