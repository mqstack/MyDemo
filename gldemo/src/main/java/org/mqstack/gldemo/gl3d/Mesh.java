package org.mqstack.gldemo.gl3d;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by mqstack on 2015/12/19.
 */
public class Mesh {

    //vetex buffer
    private FloatBuffer verticesBuffer = null;

    //index buffer
    private ShortBuffer indicesBuffer = null;

    //the number of indices
    private int numOfIndices = -1;

    //flat color
    private float[] rgba = new float[]{1.0f, 1.0f, 1.0f, 1.0f};

    //smooth colors
    private FloatBuffer colorBuffer = null;

    //Translate params
    public float x = 0;

    public float y = 0;

    public float z = 0;

    //Rotate params
    public float rx = 0;

    public float ry = 0;

    public float rz = 0;


    public void draw(GL10 gl) {
        //Counter-clockwise winding
        gl.glFrontFace(GL10.GL_CCW);

        //Enable face culling
        gl.glEnable(GL10.GL_CULL_FACE);

        //what faces to remoce with the face culling
        gl.glCullFace(GL10.GL_BACK);

        //Enabled the vertices buffer for writing and to be used during rendering
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        //Specifies the location and data format of an array of vertex coordinates to use when redering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
        //set flat color
        gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
        //Smooth color
        if (colorBuffer != null) {
            //Enable the color array buffer to be used during redering
            gl.glEnableClientState(gl.GL_COLOR_ARRAY);
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        }

        gl.glTranslatef(x, y, z);
        gl.glRotatef(rx, 1, 0, 0);
        gl.glRotatef(ry, 0, 1, 0);
        gl.glRotatef(rz, 0, 0, 1);

        //Point out the where the color buffer is
        gl.glDrawElements(GL10.GL_TRIANGLES, numOfIndices, GL10.GL_UNSIGNED_SHORT, indicesBuffer);

        //Disable the vertices buffer
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        //Disable face culling
        gl.glDisable(GL10.GL_CULL_FACE);

    }

    protected void setVertices(float[] vertices) {
        //a float is 4 bytes, therefore we multiply the number if vertices with 4
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        verticesBuffer = vbb.asFloatBuffer();
        verticesBuffer.put(vertices);
        verticesBuffer.position(0);
    }

    protected void setIndices(short[] indices) {
        // short is 2 bytes, therefore we multiply
        //the number if
        // vertices with 2.
        ByteBuffer ibb
                = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indicesBuffer = ibb.asShortBuffer();
        indicesBuffer.put(indices);
        indicesBuffer.position(0);
        numOfIndices = indices.length;
    }

    protected void setColor(float red, float green,
                            float blue, float alpha) {
        // Setting the flat color.
        rgba[0] = red;
        rgba[1] = green;
        rgba[2] = blue;
        rgba[3] = alpha;
    }

    protected void setColors(float[] colors) {
        // float has 4 bytes.
        ByteBuffer cbb
                = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

}