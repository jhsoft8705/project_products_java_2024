package CONTROLADOR;

import MODELO.ListarProductos;
import MODELO.Producto;
import MODELO.Registro;
import VISTA.frm_RegistrarProducto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane; 
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

public class Controlador_Frm_Registrar_Producto implements ActionListener, ListSelectionListener, DocumentListener {

    frm_RegistrarProducto frm_rp;

    public Controlador_Frm_Registrar_Producto(frm_RegistrarProducto frm_rp) {
        this.frm_rp = frm_rp;
        this.frm_rp.btnguardar.addActionListener(this);
        this.frm_rp.TablaProductos.getSelectionModel().addListSelectionListener(this);
        this.frm_rp.btn_actualizar.addActionListener(this);
        this.frm_rp.btn_cancelar.addActionListener(this);
        this.frm_rp.btn_eliminar.addActionListener(this);
        this.frm_rp.txt_buscar.getDocument().addDocumentListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm_rp.btnguardar) {
            String nombre = frm_rp.txtnombre.getText();
            int cantidad = Integer.parseInt(frm_rp.txtcantidad.getText());
            Double precio = Double.parseDouble(frm_rp.txtprecio.getText());
            Producto P = new Producto(nombre, cantidad, precio);
            Registro R = new Registro();
            R.registrarbd(P);
            ListarProductos lp = new ListarProductos();
            lp.MostrarTable(frm_rp.TablaProductos);
            limpiarentradas();
        }

        if (e.getSource() == frm_rp.btn_actualizar) {
            int id = Integer.parseInt(frm_rp.txtid.getText());
            String nombre = frm_rp.txtnombre.getText();
            int cantidad = Integer.parseInt(frm_rp.txtcantidad.getText());
            Double precio = Double.parseDouble(frm_rp.txtprecio.getText());
            Producto producto = new Producto(nombre, cantidad, precio);
            Registro r = new Registro();
            r.actualizarbd(producto, id);
            limpiarentradas();
            ListarProductos LP = new ListarProductos();
            LP.MostrarTable(frm_rp.TablaProductos);
        }

        if (e.getSource() == frm_rp.btn_cancelar) {

            frm_rp.btn_actualizar.setEnabled(false);
            frm_rp.btn_cancelar.setEnabled(false);
            frm_rp.btnguardar.setEnabled(true);
            limpiarentradas();
        }

        if (e.getSource() == frm_rp.btn_eliminar) {
            int FilaObtenida = frm_rp.TablaProductos.getSelectedRow();
            TableModel modelo = frm_rp.TablaProductos.getModel();
            Object id = modelo.getValueAt(FilaObtenida, 0);

            int opcion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar este producto?", "Eliminar producto", JOptionPane.YES_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                Registro R = new Registro();
                R.eliminarbd((int) id);

                ListarProductos lp = new ListarProductos();
                lp.MostrarTable(frm_rp.TablaProductos);

                limpiarentradas();

                JOptionPane.showMessageDialog(null, "REGISTRO ELIMINADO CORRETAMENTE");
            } else {
                System.out.println("NO PRESIONO NADA");
            }
        }

    }

    private void limpiarentradas() {
        frm_rp.txtid.setText("");
        frm_rp.txtnombre.setText("");
        frm_rp.txtcantidad.setText("");
        frm_rp.txtprecio.setText("");

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (e.getSource() == frm_rp.TablaProductos.getSelectionModel()) {
                int FilaObtenida = frm_rp.TablaProductos.getSelectedRow();

                if (FilaObtenida >= 0) {
                    TableModel modelo = frm_rp.TablaProductos.getModel();
                    Object id = modelo.getValueAt(FilaObtenida, 0);
                    Object nombre = modelo.getValueAt(FilaObtenida, 1);
                    Object cantidad = modelo.getValueAt(FilaObtenida, 2);
                    Object precio = modelo.getValueAt(FilaObtenida, 3);

                    frm_rp.txtid.setText(id.toString());
                    frm_rp.txtnombre.setText(nombre.toString());
                    frm_rp.txtcantidad.setText(cantidad.toString());
                    frm_rp.txtprecio.setText(precio.toString());

                    frm_rp.btnguardar.setEnabled(false);
                    frm_rp.btn_actualizar.setEnabled(true);
                    frm_rp.btn_cancelar.setEnabled(true);
                }

            }
        }
    }
 
    @Override
    public void insertUpdate(DocumentEvent e) {
        EjecutarBusqueda();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        EjecutarBusqueda();

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        EjecutarBusqueda();
    }


   public void EjecutarBusqueda() {
        String input = frm_rp.txt_buscar.getText();
        ListarProductos lp = new ListarProductos();
        lp.BuscarProducto(input, frm_rp.TablaProductos);
    }
}
