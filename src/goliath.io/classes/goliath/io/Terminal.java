/* 
 * The MIT License
 *
 * Copyright 2017 Ty Young.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package goliath.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import goliath.io.exceptions.InvalidCommandException;
import goliath.io.exceptions.InvalidWorkingDirectoryException;
import java.lang.ProcessHandle.Info;

public class Terminal
{
    private Process shell;
    private PrintWriter writer;
    private Scanner reader;
    private Scanner errorReader;
    private File workingDir;
    private String commandString;
    
    public void startCommand() throws InvalidCommandException, InvalidWorkingDirectoryException
    {   
        if(workingDir != null && !workingDir.exists())
            throw new InvalidWorkingDirectoryException();
        
        try
        {
            shell = Runtime.getRuntime().exec(commandString, null, workingDir);
        }
        catch (IOException ex)
        {
            throw new InvalidCommandException();
        }
    }
    public void print(String text)
    {
        if(writer == null)
            writer = new PrintWriter(shell.getOutputStream());
        
        writer.print(text);
    }
    public void println(String text)
    {
        if(writer == null)
            writer = new PrintWriter(shell.getOutputStream());
                    
        writer.write(text);
        writer.flush();
    }
    public void flushInput()
    {
        if(writer == null)
            writer = new PrintWriter(shell.getOutputStream());
        
        writer.flush();
    }
    public void waitForExit() throws InterruptedException
    {
        shell.waitFor();
    }
    public void terminate()
    {
        
        if(writer != null)
        {
            writer.close();
            writer = null;
        }
        if(reader != null)  
        {
            reader.close();
            reader = null;
        }
        
        if(errorReader != null)
        {
            errorReader.close();
            errorReader = null;
        }
        
        shell.destroy();   
    }
    public Info getInfo()
    {
        return shell.info();
    }
    public long getPID()
    {
        return shell.pid();
    }
    public int getExitCode()
    {
        return shell.exitValue();
    }
    public PrintWriter getCommandPrinter()
    {
        if(writer == null)
            writer = new PrintWriter(shell.getOutputStream());
        
        return writer;
    }
    public Scanner getCommandReader()
    {
        if(reader == null)
            reader = new Scanner(shell.getInputStream());
        
        return reader;
    }
    public Scanner getCommandErrorReader()
    {
        if(errorReader == null)
            errorReader = new Scanner(shell.getErrorStream());
        
        return errorReader;
    }
    public File getWorkingDirectory()
    {
        return workingDir;
    }
    public OutputStream getOutputStream()
    {
        return shell.getOutputStream();
    }
    public InputStream getInputStream()
    {
        return shell.getInputStream();
    }
    public InputStream getErrorStream()
    {
        return shell.getErrorStream();
    }
    public void setCommand(String cmd)
    {
        commandString = cmd;
    }
    public void setWorkingDirectory(File dir)
    {
        workingDir = dir;
    }
}