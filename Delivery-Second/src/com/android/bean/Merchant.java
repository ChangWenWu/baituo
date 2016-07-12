package com.android.bean;

import java.util.Set;

public abstract interface Merchant
{
  public abstract long getAccount();

  public abstract void setAccount(long paramLong);

  public abstract String getName();

  public abstract void setName(String paramString);

  public abstract String getPhone();

  public abstract void setPhone(String paramString);

  public abstract String getPassword();

  public abstract void setPassword(String paramString);

  public abstract Set<Message> getMessage();

  public abstract void setMessage(Set<Message> paramSet);
}

