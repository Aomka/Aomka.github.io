const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

// สร้างแมว
let cat = {
    x: 50,
    y: 200,
    width: 50,
    height: 50,
    speed: 5,
    draw: function() {
        ctx.fillStyle = 'orange';
        ctx.fillRect(this.x, this.y, this.width, this.height);
    }
};

// สร้างมอนสเตอร์
let monsters = [];
function createMonster() {
    let monster = {
        x: canvas.width,
        y: Math.random() * (canvas.height - 50),
        width: 50,
        height: 50,
        speed: 3,
        draw: function() {
            ctx.fillStyle = 'red';
            ctx.fillRect(this.x, this.y, this.width, this.height);
        }
    };
    monsters.push(monster);
}

// ยิงมอนสเตอร์
let bullets = [];
function shoot() {
    let bullet = {
        x: cat.x + cat.width,
        y: cat.y + cat.height / 2,
        width: 10,
        height: 5,
        speed: 7,
        draw: function() {
            ctx.fillStyle = 'yellow';
            ctx.fillRect(this.x, this.y, this.width, this.height);
        }
    };
    bullets.push(bullet);
}

function updateGame() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // วาดแมว
    cat.draw();

    // วาดและเคลื่อนที่กระสุน
    bullets.forEach((bullet, index) => {
        bullet.x += bullet.speed;
        bullet.draw();
        if (bullet.x > canvas.width) {
            bullets.splice(index, 1); // ลบกระสุนที่ออกนอกจอ
        }
    });

    // วาดและเคลื่อนที่มอนสเตอร์
    monsters.forEach((monster, index) => {
        monster.x -= monster.speed;
        monster.draw();

        // ตรวจจับการชน
        bullets.forEach((bullet, bulletIndex) => {
            if (bullet.x < monster.x + monster.width &&
                bullet.x + bullet.width > monster.x &&
                bullet.y < monster.y + monster.height &&
                bullet.y + bullet.height > monster.y) {
                monsters.splice(index, 1); // ลบมอนสเตอร์ที่โดนยิง
                bullets.splice(bulletIndex, 1); // ลบกระสุนที่ยิงโดน
            }
        });

        if (monster.x + monster.width < 0) {
            monsters.splice(index, 1); // ลบมอนสเตอร์ที่วิ่งออกนอกจอ
        }
    });
}

function gameLoop() {
    updateGame();
    requestAnimationFrame(gameLoop);
}

// สร้างมอนสเตอร์ใหม่ทุก ๆ 2 วินาที
setInterval(createMonster, 2000);

// ตรวจจับการคลิกเพื่อยิง
canvas.addEventListener('click', shoot);

gameLoop();
