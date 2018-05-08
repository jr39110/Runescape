
public class Packet {

    private int pID;

    private int pLength;

	private final byte[] pData;

    private int caret = 0;

	private boolean bare;
	 
     public Packet(byte[] pData){
         this.pData = pData;
     }




	public boolean isBare() {
		return bare;
	}

	public int getID() {
		return pID;
	}

	public int getLength() {
		return pLength;
	}

	public byte[] getData() {
		return pData;
	}

    public byte readSignedByteA()
    {
        return(byte) (readByte() - 128);
    }

    public byte readSignedByteC()
    {
        return(byte) ( -readByte());
    }

    public byte readSignedByteS()
    {
        return(byte) (128 - readByte());
    }

    public int readUnsignedByteA()
    {
        return readByte() - 128 & 0xff;
    }


    public int readUnsignedByteC()
    {
        return -readByte() & 0xff;
    }


    public int readUnsignedByteS()
    {
        return 128 - readByte() & 0xff;
    }

    public int readSignedWordBigEndian()
    {
        int i = ((readByte() & 0xff) << 8) + (readByte() & 0xff);
        if(i > 32767){
            i -= 0x10000;
        }
        return i;
    }


    public int readSignedWordA()
    {
        int i = ((readByte() & 0xff) << 8) + (readByte() - 128 & 0xff);
        if(i > 32767){
            i -= 0x10000;
        }
        return i;
    }


    public int readSignedWordBigEndianA()
    {
        int i = ((readByte() & 0xff) << 8) + (readByte() - 128 & 0xff);
        if(i > 32767)
            i -= 0x10000;
        return i;
    }


    public int readUnsignedWordBigEndian()
    {
        return((readByte() & 0xff) << 8) + (readByte() & 0xff);
    }


    public int readUnsignedWordA()
    {
        return((readByte() & 0xff) << 8) + (readByte() - 128 & 0xff);
    }


    public int readUnsignedWordBigEndianA()
    {
        return((readByte() & 0xff) << 8) + (readByte()- 128 & 0xff);
    }

    public int readDWord_v1()
    {
        return((readByte() & 0xff) << 24) + ((readByte() & 0xff) << 16) +
            ((readByte() & 0xff) << 8) + (readByte() & 0xff);
    }


    public int readDWord_v2()
    {
        return((readByte() & 0xff) << 24) + ((readByte() & 0xff) << 16) +
            ((readByte() & 0xff) << 8) + (readByte() & 0xff);
    }


    public void readBytes_reverse(byte abyte0[], int i, int j)
    {
        for(int k = (j + i) - 1; k >= j; k--){
            abyte0[k] = readByte();
        }
    }


    public void readBytes_reverseA(byte abyte0[], int i, int j)
    {
        for(int k = (j + i) - 1; k >= j; k--)
            abyte0[k] = (byte) (readByte() - 128);

    }

    public int readUnsignedByte()
    {
        return readByte() & 0xff;
    }


    public byte readSignedByte()
    {
        return readByte();
    }

    public int readUnsignedWord()
    {
        return((readByte() & 0xff) << 8) + (readByte() & 0xff);
    }

    public int readSignedWord()
    {
        int i = ((readByte() & 0xff) << 8) + (readByte() & 0xff);
        if(i > 32767){
            i -= 0x10000;
        }
        return i;
    }

    public int readDWord()
    {
        return((readByte() & 0xff) << 24) + ((readByte() & 0xff) << 16) +
            ((readByte() & 0xff) << 8) + (readByte() & 0xff);
    }

    public long readQWord()
    {
        long l = (long) readDWord() & 0xffffffffL;
        long l1 = (long) readDWord() & 0xffffffffL;
        return(l << 32) + l1;
    }


    public String readString() {
        String s = "";
        int b;
        while((b = readByte()) != 0){
			s += (char)b;
		}
        return s;
    }


    public void readBytes(byte abyte0[], int i, int j)
    {
        for(int k = j; k < j + i; k++)
            abyte0[k] = readByte();

    }



     public byte readByte() {
		try{
			return pData[caret++];
		} catch (Exception _ex) {return -1;}
	}




	public void skip(int x) {
		caret += x;
	}


	public int remaining() {
		return pData.length - caret;
	}

} 

