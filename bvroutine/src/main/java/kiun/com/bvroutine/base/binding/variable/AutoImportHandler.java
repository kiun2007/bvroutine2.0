package kiun.com.bvroutine.base.binding.variable;

/**
 * 自动导入实施者.
 * @param <T> 导入的实例类型.
 */
@FunctionalInterface
public interface AutoImportHandler<T> {

    void onImportComplete(T value);
}
