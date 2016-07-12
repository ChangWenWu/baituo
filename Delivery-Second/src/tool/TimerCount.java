package tool;
import android.R;
import android.os.CountDownTimer;
import android.widget.Button;

public class TimerCount extends CountDownTimer{
	
	private Button bnt;

	public TimerCount(long millisInFuture, long countDownInterval, Button bnt) {
		super(millisInFuture, countDownInterval);
		this.bnt = bnt;
	}

	public TimerCount(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		bnt.setClickable(true);
		bnt.setText("获取验证码");
	}

	@Override
	public void onTick(long arg0) {
		// TODO Auto-generated method stub
		bnt.setClickable(false);
		
        bnt.setText(arg0 / 1000+"秒后重新获取");
	}
}